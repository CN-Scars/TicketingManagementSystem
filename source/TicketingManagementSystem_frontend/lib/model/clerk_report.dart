class ClerkReport {
  final String clerkName;
  final DateTime date;
  final double charge;

  ClerkReport({
    required this.clerkName,
    required this.date,
    required this.charge,
  });

  Map<String, dynamic> toJson() => {
    'clerkName': clerkName,
    'date': date.toIso8601String(),
    'charge': charge,
  };

  factory ClerkReport.fromJson(Map<String, dynamic> json) {
    return ClerkReport(
      clerkName: json['clerkName'],
      date: DateTime.parse(json['date']),
      charge: json['charge'],
    );
  }
}