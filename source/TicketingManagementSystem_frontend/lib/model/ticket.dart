class Ticket {
  final int ticketID;
  final String ticketName;
  final double price;
  final int stock;

  Ticket({
    required this.ticketID,
    required this.ticketName,
    required this.price,
    required this.stock,
  });

  Map<String, dynamic> toJson() => {
    'ticketID': ticketID,
    'ticketName': ticketName,
    'price': price,
    'stock': stock,
  };

  factory Ticket.fromJson(Map<String, dynamic> json) {
    return Ticket(
      ticketID: json['ticketID'],
      ticketName: json['ticketName'],
      price: json['price'],
      stock: json['stock'],
    );
  }
}
