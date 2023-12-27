import 'package:flutter/material.dart';

class DateEditingController extends TextEditingController {
  DateTime date;

  DateEditingController({DateTime? date})
      : date = date ?? DateTime.now(),
        super(text: (date ?? DateTime.now()).toIso8601String());

  @override
  set text(String newText) {
    date = DateTime.parse(newText);
    super.text = newText;
  }

  @override
  String get text => date.toIso8601String();
}