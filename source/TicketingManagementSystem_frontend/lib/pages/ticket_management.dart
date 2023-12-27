import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:ticketing_management_system/controllers/DateEditingController.dart';
import 'dart:convert';

import '../model/ticket.dart';

class TicketManagementPage extends StatefulWidget {
  @override
  _TicketManagementPageState createState() => _TicketManagementPageState();
}

class _TicketManagementPageState extends State<TicketManagementPage> {
  List<Ticket> tickets = [];
  final TextEditingController ticketNameController = TextEditingController();
  final TextEditingController priceController = TextEditingController();
  final DateEditingController validityPeriodController =
      DateEditingController();
  final TextEditingController stockController = TextEditingController();

  var selectedDate = DateTime.now();

  @override
  void initState() {
    super.initState();
    fetchTickets();
  }

  Future<void> fetchTickets() async {
    final response = await http.get(Uri.parse('http://localhost:8080/tickets'));

    if (response.statusCode == 200) {
      setState(() {
        var ticketList = json.decode(utf8.decode(response.bodyBytes)) as List;
        tickets = ticketList.map((ticket) => Ticket.fromJson(ticket)).toList();
      });
    } else {
      print('获取门票列表失败');
    }
  }

  Future<void> searchTickets() async {
    final response = await http.get(Uri.parse(
        'http://localhost:8080/tickets/search?ticketName=${ticketNameController.text}&price=${priceController.text}&stock=${stockController.text}')); // 移除validityPeriod参数

    if (response.statusCode == 200) {
      setState(() {
        var ticketList = json.decode(utf8.decode(response.bodyBytes)) as List;
        tickets = ticketList.map((ticket) => Ticket.fromJson(ticket)).toList();
      });
    } else {
      print('搜索门票失败');
    }
  }

  Future<void> updateTicket(Ticket ticket, Function onUpdate) async {
    final response = await http.put(
      Uri.parse('http://localhost:8080/tickets'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(ticket.toJson()),
    );

    if (response.statusCode == 200) {
      print('门票信息更新成功');
      onUpdate();
    } else {
      print('门票信息更新失败');
    }
  }

  Future<void> deleteTicket(Ticket ticket, Function onUpdate) async {
    final response = await http.delete(
      Uri.parse('http://localhost:8080/tickets/${ticket.ticketID}'),
    );

    if (response.statusCode == 200) {
      print('门票删除成功');
      onUpdate();
    } else {
      print('门票删除失败');
    }
  }

  Future<void> addTicket(Ticket ticket, Function onUpdate) async {
    final response = await http.post(
      Uri.parse('http://localhost:8080/tickets'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(ticket.toJson()),
    );

    if (response.statusCode == 200) {
      print('门票创建成功');
      onUpdate();
    } else {
      print('门票创建失败');
    }
  }

  void showUpdateDialog(BuildContext context, Ticket ticket) {
    final TextEditingController ticketNameController =
    TextEditingController(text: ticket.ticketName);
    final TextEditingController priceController =
    TextEditingController(text: ticket.price.toString());
    final TextEditingController stockController =
    TextEditingController(text: ticket.stock.toString());

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('修改门票信息'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: ticketNameController,
                decoration: InputDecoration(
                  labelText: '门票名称',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: priceController,
                decoration: InputDecoration(
                  labelText: '价格',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: stockController,
                decoration: InputDecoration(
                  labelText: '库存量',
                  border: OutlineInputBorder(),
                ),
                keyboardType: TextInputType.number,
              ),
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('取消'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('确认'),
              onPressed: () {
                Ticket updatedTicket = Ticket(
                  ticketID: ticket.ticketID,
                  ticketName: ticketNameController.text,
                  price: double.parse(priceController.text),
                  stock: int.parse(stockController.text),
                );
                updateTicket(updatedTicket, fetchTickets);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void showDeleteDialog(BuildContext context, Ticket ticket) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('删除门票信息'),
          content: Text('你确定要删除 ${ticket.ticketName} 的信息吗？'),
          actions: <Widget>[
            TextButton(
              child: Text('取消'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('确认'),
              onPressed: () {
                deleteTicket(ticket, fetchTickets);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void showAddDialog(BuildContext context) {
    final TextEditingController ticketNameController = TextEditingController();
    final TextEditingController priceController = TextEditingController();
    final TextEditingController stockController = TextEditingController();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('添加门票'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: ticketNameController,
                decoration: InputDecoration(
                  labelText: '门票名称',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: priceController,
                decoration: InputDecoration(
                  labelText: '价格',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: stockController,
                decoration: InputDecoration(
                  labelText: '库存量',
                  border: OutlineInputBorder(),
                ),
                keyboardType: TextInputType.number,
              ),
              // 移除日期选择器相关的代码
            ],
          ),
          actions: <Widget>[
            TextButton(
              child: Text('取消'),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
            TextButton(
              child: Text('确认'),
              onPressed: () {
                Ticket newTicket = Ticket(
                  ticketID: 0,
                  // 服务器应该忽略这个id并自动生成一个新的id
                  ticketName: ticketNameController.text,
                  price: double.parse(priceController.text),
                  stock: int.parse(stockController.text),
                );
                addTicket(newTicket, fetchTickets);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('门票管理'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: <Widget>[
            Row(
              children: <Widget>[
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      controller: ticketNameController,
                      decoration: InputDecoration(
                        labelText: '门票名称',
                        border: OutlineInputBorder(),
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      controller: priceController,
                      decoration: InputDecoration(
                        labelText: '价格',
                        border: OutlineInputBorder(),
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      controller: stockController,
                      decoration: InputDecoration(
                        labelText: '库存量',
                        border: OutlineInputBorder(),
                      ),
                      keyboardType: TextInputType.number,
                    ),
                  ),
                ),
                // 移除有效日期输入框
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: IconButton(
                    icon: Icon(Icons.search),
                    onPressed: searchTickets,
                  ),
                ),
              ],
            ),
            Expanded(
              child: ListView.builder(
                itemCount: tickets.length,
                itemBuilder: (context, index) {
                  return Card(
                    child: ListTile(
                      title: Text(tickets[index].ticketName),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Text('价格: ${tickets[index].price.toStringAsFixed(2)}'),
                          Text('库存: ${tickets[index].stock}'),
                        ],
                      ),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: <Widget>[
                          IconButton(
                            icon: Icon(Icons.edit),
                            onPressed: () {
                              showUpdateDialog(context, tickets[index]);
                            },
                          ),
                          IconButton(
                            icon: Icon(Icons.delete),
                            onPressed: () {
                              showDeleteDialog(context, tickets[index]);
                            },
                          ),
                        ],
                      ),
                    ),
                  );
                },
              ),
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          showAddDialog(context);
        },
        child: Icon(Icons.add),
      ),
    );
  }
}
