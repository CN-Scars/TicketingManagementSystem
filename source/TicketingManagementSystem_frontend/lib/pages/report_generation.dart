import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:fl_chart/fl_chart.dart';
import 'package:ticketing_management_system/model/clerk_report.dart';
import 'package:ticketing_management_system/model/report.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class ReportGenerationPage extends StatefulWidget {
  @override
  _ReportGenerationPageState createState() => _ReportGenerationPageState();
}

class _ReportGenerationPageState extends State<ReportGenerationPage> {
  // 基础宽度
  static const double baseWidth = 50.0;
  // 最大和最小宽度限制
  static const double maxWidth = 60.0;
  static const double minWidth = 20.0;

  Future<List<Report>>? futureReports;
  Future<List<ClerkReport>>? futureClerkReports;
  DateTime selectedDate = DateTime.now();
  String queryMode = 'day'; // 查询模式，'day'表示按日查询，'month'表示按月查询

  @override
  void initState() {
    super.initState();
    futureReports = fetchReports();
    futureClerkReports = fetchClerkReports();

  }

  Future<List<Report>> fetchReports() async {
    final response =
        await http.get(Uri.parse('http://localhost:8080/report/reports'));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(response.body);
      return jsonResponse.map((item) => Report.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load reports from API');
    }
  }

  Future<List<ClerkReport>> fetchClerkReports() async {
    final response =
        await http.get(Uri.parse('http://localhost:8080/report/clerk_reports'));

    if (response.statusCode == 200) {
      List jsonResponse = json.decode(utf8.decode(response.bodyBytes));
      return jsonResponse.map((item) => ClerkReport.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load clerk reports from API');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('数据报表'),
      ),
      body: Column(
        children: <Widget>[
          DropdownButton<String>(
            value: queryMode,
            items: <DropdownMenuItem<String>>[
              DropdownMenuItem<String>(
                value: 'day',
                child: Text('按日查询'),
              ),
              DropdownMenuItem<String>(
                value: 'month',
                child: Text('按月查询'),
              ),
            ],
            onChanged: (String? newValue) {
              setState(() {
                queryMode = newValue!;
              });
            },
          ),
          ElevatedButton(
            child: Text('选择日期'),
            onPressed: () async {
              final DateTime? picked = await showDatePicker(
                context: context,
                initialDate: selectedDate,
                firstDate: DateTime(2000),
                lastDate: DateTime.now(),
                initialDatePickerMode: queryMode == 'day'
                    ? DatePickerMode.day
                    : DatePickerMode.year,
              );
              if (picked != null && picked != selectedDate) {
                setState(() {
                  selectedDate = picked;
                });
              }
            },
          ),
          FutureBuilder<List<Report>>(
            future: futureReports,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                List<Report> reports = snapshot.data!;
                if (queryMode == 'month') {
                  Map<String, Report> monthlyReports = {};
                  for (var report in reports) {
                    String monthKey = DateFormat('yyyy-MM').format(report.date);
                    if (monthlyReports.containsKey(monthKey)) {
                      var oldReport = monthlyReports[monthKey]!;
                      var newTotalSales =
                          oldReport.totalSales + report.totalSales;
                      var newSalesByPrice =
                          Map<double, int>.from(oldReport.salesByPrice);
                      report.salesByPrice.forEach((key, value) {
                        if (newSalesByPrice.containsKey(key)) {
                          newSalesByPrice[key] = newSalesByPrice[key]! + value;
                        } else {
                          newSalesByPrice[key] = value;
                        }
                      });
                      monthlyReports[monthKey] = Report(
                        date: oldReport.date,
                        totalSales: newTotalSales,
                        salesByPrice: newSalesByPrice,
                      );
                    } else {
                      monthlyReports[monthKey] = Report(
                        date: report.date,
                        totalSales: report.totalSales,
                        salesByPrice:
                            Map<double, int>.from(report.salesByPrice),
                      );
                    }
                  }
                  reports = monthlyReports.values.toList();
                }
                return Expanded(
                  child: ListView.builder(
                    itemCount: reports.length,
                    itemBuilder: (context, index) {
                      if (queryMode == 'day' &&
                          DateFormat('yyyy-MM-dd')
                                  .format(reports[index].date) !=
                              DateFormat('yyyy-MM-dd').format(selectedDate)) {
                        return Container();
                      } else if (queryMode == 'month' &&
                          DateFormat('yyyy-MM').format(reports[index].date) !=
                              DateFormat('yyyy-MM').format(selectedDate)) {
                        return Container();
                      }
                      return Padding(
                        padding: const EdgeInsets.all(8.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                            Text(
                                '日期: ${DateFormat('yyyy-MM-dd').format(reports[index].date)}'),
                            SizedBox(height: 4),
                            Text('总销售量: ${reports[index].totalSales}'),
                            SizedBox(height: 4),
                            SizedBox(
                              height: 200,
                              child: BarChart(
                                BarChartData(
                                  maxY: reports[index].salesByPrice.isNotEmpty
                                      ? reports[index]
                                      .salesByPrice
                                      .values
                                      .reduce((curr, next) => curr > next ? curr : next)
                                      .toDouble()
                                      : 1,
                                  barTouchData: BarTouchData(
                                    enabled: true, // 允许接收触摸事件
                                  ),
                                  barGroups: reports[index]
                                      .salesByPrice
                                      .entries
                                      .map(
                                        (entry) {
                                      // 计算宽度
                                      double calculatedWidth = baseWidth - (reports[index].salesByPrice.length * 3);
                                      calculatedWidth = calculatedWidth.clamp(minWidth, maxWidth);

                                      return BarChartGroupData(
                                        x: entry.key.toInt(),
                                        barRods: [
                                          BarChartRodData(
                                            y: entry.value.toDouble(),
                                            colors: [Colors.blue],
                                            width: calculatedWidth,
                                          ),
                                        ],
                                      );
                                    },
                                  )
                                      .toList(),
                                ),
                              ),
                            ),
                            SizedBox(height: 16),
                          ],
                        ),
                      );
                    },
                  ),
                );
              } else if (snapshot.hasError) {
                return Text("${snapshot.error}");
              }
              return CircularProgressIndicator();
            },
          ),
          FutureBuilder<List<ClerkReport>>(
            future: futureClerkReports,
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return DataTable(
                  columns: const <DataColumn>[
                    DataColumn(
                      label: Text('售票员'),
                    ),
                    DataColumn(
                      label: Text('日期'),
                    ),
                    DataColumn(
                      label: Text('收费'),
                    ),
                  ],
                  rows: snapshot.data!.map((ClerkReport report) {
                    return DataRow(
                      cells: <DataCell>[
                        DataCell(Text(report.clerkName)),
                        DataCell(
                            Text(DateFormat('yyyy-MM-dd').format(report.date))),
                        DataCell(Text(report.charge.toString())),
                      ],
                    );
                  }).toList(),
                );
              } else if (snapshot.hasError) {
                return Text("${snapshot.error}");
              }
              return CircularProgressIndicator();
            },
          ),
        ],
      ),
    );
  }
}
