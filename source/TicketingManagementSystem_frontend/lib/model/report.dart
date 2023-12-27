// import 'dart:ffi';

class Report {
  final DateTime date;
  final int totalSales;
  final Map<double, int> salesByPrice;

  Report({
    required this.date,
    required this.totalSales,
    required this.salesByPrice,
  });

  factory Report.fromJson(Map<String, dynamic> json) {
    return Report(
      date: DateTime.parse(json['date']),
      totalSales: json['totalSales'],
      salesByPrice: Map<String, dynamic>.from(json['salesByPrice']).map(
        (key, value) => MapEntry(double.parse(key), value as int),
      ),
    );
  }
}
