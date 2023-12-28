class User {
  final int id;
  final String name;
  final String email;
  final int age;

  User({required this.id, required this.name, required this.email, required this.age});

  Map<String, dynamic> toJson() => {
    'id': id,
    'name': name,
    'email': email,
    'age': age,
  };

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      name: json['name'],
      email: json['email'],
      age: json['age'],
    );
  }
}