import 'package:flutter/material.dart';
import 'package:ticketing_management_system/pages/order_management.dart';
import 'package:ticketing_management_system/pages/report_generation.dart';
import 'package:ticketing_management_system/pages/ticket_management.dart';
import 'package:ticketing_management_system/pages/user_management.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Ticket Management System',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 0;
  static final List<Widget> _widgetOptions = <Widget>[
    UserManagementPage(),
    TicketManagementPage(),
    OrderManagementPage(),
    ReportGenerationPage(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('景点门票销售管理系统'),
      ),
      body: Center(
        child: _widgetOptions.elementAt(_selectedIndex),
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle),
            label: '用户管理',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.local_activity),
            label: '门票管理',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.list),
            label: '订单管理',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.bar_chart),
            label: '数据报表',
          ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.amber[800],
        onTap: _onItemTapped,
      ),
    );
  }
}
