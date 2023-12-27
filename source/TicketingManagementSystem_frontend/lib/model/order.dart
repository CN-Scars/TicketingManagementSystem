class Order {
  final int orderID;
  final String userName; // 用户名
  final String ticketName; // 门票名称
  final int quantity;
  final double totalPrice;
  final String orderDate;
  final String sellerName; // 售票员名字
  final int state;

  Order({
    required this.orderID,
    required this.userName,
    required this.ticketName,
    required this.quantity,
    required this.totalPrice,
    required this.orderDate,
    required this.sellerName,
    required this.state,
  });

  Map<String, dynamic> toJson() => {
    'orderID': orderID,
    'userName': userName,
    'ticketName': ticketName,
    'quantity': quantity,
    'totalPrice': totalPrice,
    'orderDate': orderDate,
    'sellerName': sellerName,
    'state': state,
  };

  factory Order.fromJson(Map<String, dynamic> json) {
    return Order(
      orderID: json['orderID'],
      userName: json['userName'],
      ticketName: json['ticketName'],
      quantity: json['quantity'],
      totalPrice: json['totalPrice'],
      orderDate: json['orderDate'],
      sellerName: json['sellerName'],
      state: json['state'],
    );
  }
}