import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

import '../model/user.dart';

class UserManagementPage extends StatefulWidget {
  @override
  _UserManagementPageState createState() => _UserManagementPageState();
}

class _UserManagementPageState extends State<UserManagementPage> {
  List<User> users = [];
  final TextEditingController nameController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController ageController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchUsers();
  }

  /**
   * 获取用户列表
   */
  Future<void> fetchUsers() async {
    final response = await http.get(Uri.parse('http://localhost:8080/users'));

    if (response.statusCode == 200) {
      setState(() {
        var userList = json.decode(utf8.decode(response.bodyBytes)) as List;
        users = userList.map((user) => User.fromJson(user)).toList();
      });
    } else {
      print('获取用户列表失败');
    }
  }

  /**
   * 搜索用户
   */
  Future<void> searchUsers() async {
    final response = await http.get(Uri.parse(
        'http://localhost:8080/users/search?name=${nameController.text}&email=${emailController.text}'));

    if (response.statusCode == 200) {
      setState(() {
        var userList = json.decode(utf8.decode(response.bodyBytes)) as List;
        users = userList.map((user) => User.fromJson(user)).toList();
      });
    } else {
      print('搜索用户失败');
    }
  }

  /**
   * 更新用户信息
   */
  Future<void> updateUser(User user, Function onUpdate) async {
    final response = await http.put(
      Uri.parse('http://localhost:8080/users'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(user.toJson()),
    );

    if (response.statusCode == 200) {
      print('用户信息更新成功');
      onUpdate();
    } else {
      print('用户信息更新失败');
    }
  }

  Future<void> deleteUser(User user, Function onUpdate) async {
    final response = await http.delete(
      Uri.parse('http://localhost:8080/users/${user.id}'),
    );

    if (response.statusCode == 200) {
      print('用户删除成功');
      onUpdate();
    } else {
      print('用户删除失败');
    }
  }

  Future<void> addUser(User newUser, Function onUpdate) async {
    final response = await http.post(
      Uri.parse('http://localhost:8080/users'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(newUser.toJson()),
    );

    if (response.statusCode == 200) {
      print('用户添加成功');
      onUpdate();
    } else {
      print('用户添加失败');
    }
  }

  void showUpdateDialog(BuildContext context, User user) {
    final TextEditingController nameController =
        TextEditingController(text: user.name);
    final TextEditingController emailController =
        TextEditingController(text: user.email);
    final TextEditingController ageController =
        TextEditingController(text: user.age.toString()); // 添加这行代码

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('修改用户信息'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: nameController,
                decoration: InputDecoration(
                  labelText: '姓名',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: '邮箱',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: ageController,
                decoration: InputDecoration(
                  labelText: '年龄',
                  border: OutlineInputBorder(),
                ),
              )
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
                User updatedUser = User(
                  id: user.id,
                  name: nameController.text,
                  email: emailController.text,
                  age: int.parse(ageController.text),
                );
                updateUser(updatedUser, fetchUsers);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void showDeleteDialog(BuildContext context, User user) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('删除用户信息'),
          content: Text('你确定要删除 ${user.name} 的信息吗？'),
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
                deleteUser(user, fetchUsers);
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

  void showAddDialog(BuildContext context) {
    final TextEditingController nameController = TextEditingController();
    final TextEditingController emailController = TextEditingController();
    final TextEditingController ageController = TextEditingController();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('添加用户'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: <Widget>[
              TextField(
                controller: nameController,
                decoration: InputDecoration(
                  labelText: '姓名',
                  border: OutlineInputBorder(),
                ),
              ),
              SizedBox(height: 8.0),
              TextField(
                controller: emailController,
                decoration: InputDecoration(
                  labelText: '邮箱',
                  border: OutlineInputBorder(),
                ),
              ),
              TextField(
                controller: ageController,
                decoration: InputDecoration(
                  labelText: '年龄',
                  border: OutlineInputBorder(),
                ),
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
                if (int.tryParse(ageController.text) == null) {
                  print('请输入有效的年龄');
                  return;
                }
                User newUser = User(
                  id: 0, // 服务器应该忽略这个id并自动生成一个新的id
                  name: nameController.text,
                  email: emailController.text,
                  age: int.parse(ageController.text),
                );
                addUser(newUser, fetchUsers);
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
        title: Text('用户管理'),
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
                      controller: nameController,
                      decoration: InputDecoration(
                        labelText: '姓名',
                        border: OutlineInputBorder(),
                      ),
                    ),
                  ),
                ),
                Expanded(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: TextField(
                      controller: emailController,
                      decoration: InputDecoration(
                        labelText: '邮箱',
                        border: OutlineInputBorder(),
                      ),
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: IconButton(
                    icon: Icon(Icons.search),
                    onPressed: searchUsers,
                  ),
                ),
              ],
            ),
            Expanded(
              child: ListView.builder(
                itemCount: users.length,
                itemBuilder: (context, index) {
                  return Card(
                    child: ListTile(
                      title: Text(users[index].name),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          Text(users[index].email),
                          Text('年龄: ${users[index].age}'),
                        ],
                      ),
                      trailing: Row(
                        mainAxisSize: MainAxisSize.min,
                        children: <Widget>[
                          IconButton(
                            icon: Icon(Icons.edit),
                            onPressed: () {
                              showUpdateDialog(context, users[index]);
                            },
                          ),
                          IconButton(
                            icon: Icon(Icons.delete),
                            onPressed: () {
                              showDeleteDialog(context, users[index]);
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
