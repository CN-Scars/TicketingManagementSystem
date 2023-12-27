import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:ticketing_management_system/model/order.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class OrderManagementPage extends StatefulWidget {
  @override
  _OrderManagementPageState createState() => _OrderManagementPageState();
}

class _OrderManagementPageState extends State<OrderManagementPage> {
  List<Order> orders = [];
  final TextEditingController orderIDController = TextEditingController();
  final TextEditingController userNameController =
      TextEditingController(); // 用户名
  final TextEditingController ticketNameController =
      TextEditingController(); // 门票名称
  final TextEditingController orderDateController = TextEditingController();
  final TextEditingController sellerNameController =
      TextEditingController(); // 售票员姓名
  final TextEditingController stateController = TextEditingController();
  String? selectedState;
  DateTime selectedDate = DateTime.now();

  @override
  void initState() {
    super.initState();

    fetchOrders();
  }

  Future<void> selectDate(BuildContext context) async {
    final DateTime? pickedDate = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (pickedDate != null && pickedDate != selectedDate) {
      final TimeOfDay? pickedTime = await showTimePicker(
        context: context,
        initialTime: TimeOfDay.fromDateTime(selectedDate),
      );
      if (pickedTime != null) {
        setState(() {
          selectedDate = DateTime(
            pickedDate.year,
            pickedDate.month,
            pickedDate.day,
            pickedTime.hour,
            pickedTime.minute,
            // 0,
          );
          orderDateController.text = selectedDate.toIso8601String();
        });
      }
    }
  }

  Future<void> fetchOrders() async {
    final response = await http.get(Uri.parse('http://localhost:8080/orders'));

    if (response.statusCode == 200) {
      setState(() {
        var orderList = json.decode(utf8.decode(response.bodyBytes)) as List;
        orders = orderList.map((order) => Order.fromJson(order)).toList();
      });
    } else {
      print('获取订单列表失败');
    }
  }

  Future<void> refundOrder(Order order) async {
    final response = await http.put(
      Uri.parse('http://localhost:8080/orders/${order.orderID}'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode({'state': 0}),
    );

    if (response.statusCode == 200) {
      print('订单退票成功');
      fetchOrders();
    } else {
      print('订单退票失败');
    }
  }

  Future<void> searchOrders() async {
    final response = await http.get(Uri.parse(
        'http://localhost:8080/orders/search?orderID=${orderIDController.text}&userName=${userNameController.text}&ticketName=${ticketNameController.text}&orderDate=${orderDateController.text}&sellerName=${sellerNameController.text}&state=${stateController.text}'));

    if (response.statusCode == 200) {
      setState(() {
        var orderList = json.decode(utf8.decode(response.bodyBytes)) as List;
        orders = orderList.map((order) => Order.fromJson(order)).toList();
      });
    } else {
      print('搜索订单失败');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('订单管理'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: <Widget>[
            Row(
              children: <Widget>[
                Expanded(
                  child: TextField(
                    controller: orderIDController,
                    decoration: InputDecoration(
                      labelText: '订单ID',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                Expanded(
                  child: TextField(
                    controller: userNameController,
                    decoration: InputDecoration(
                      labelText: '用户名',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                Expanded(
                  child: TextField(
                    controller: ticketNameController,
                    decoration: InputDecoration(
                      labelText: '门票名称',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                Expanded(
                  child: TextField(
                    controller: orderDateController,
                    decoration: InputDecoration(
                      labelText: '下单时间',
                      border: OutlineInputBorder(),
                    ),
                    readOnly: true,
                    onTap: () {
                      selectDate(context);
                    },
                  ),
                ),
                Expanded(
                  child: TextField(
                    controller: sellerNameController,
                    decoration: InputDecoration(
                      labelText: '售票员姓名',
                      border: OutlineInputBorder(),
                    ),
                  ),
                ),
                Expanded(
                  child: DropdownButton<String>(
                    value: selectedState,
                    hint: Text('订单状态'),
                    items: <DropdownMenuItem<String>>[
                      DropdownMenuItem<String>(
                        value: null,
                        child: Text('请选择'),
                      ),
                      DropdownMenuItem<String>(
                        value: '1',
                        child: Text('已购买'),
                      ),
                      DropdownMenuItem<String>(
                        value: '0',
                        child: Text('已退票'),
                      ),
                    ],
                    onChanged: (String? newValue) {
                      setState(() {
                        selectedState = newValue;
                        stateController.text = newValue ?? '';
                      });
                    },
                  ),
                ),
                IconButton(
                  icon: Icon(Icons.search),
                  onPressed: searchOrders,
                ),
              ],
            ),
            Expanded(
              child: ListView.builder(
                itemCount: orders.length,
                itemBuilder: (context, index) {
                  return Card(
                    child: ListTile(
                      title: Text('订单ID: ${orders[index].orderID}'),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Text('用户名: ${orders[index].userName}'),
                          Text('门票名称: ${orders[index].ticketName}'),
                          Text('购买数量: ${orders[index].quantity}'),
                          Text(
                              '总价: ${orders[index].totalPrice.toStringAsFixed(2)}'),
                          Text('下单时间: ${DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.parse(orders[index].orderDate))}'), // 精确到秒
                          Text('售票员姓名: ${orders[index].sellerName}'),
                          Text(
                              '订单状态: ${orders[index].state == 1 ? '已购买' : '已退票'}'),
                        ],
                      ),
                      trailing: orders[index].state == 1
                          ? ElevatedButton(
                              child: Text('退单'),
                              onPressed: () {
                                refundOrder(orders[index]);
                              },
                            )
                          : null,
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
