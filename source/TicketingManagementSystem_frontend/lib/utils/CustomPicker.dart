import 'package:flutter/cupertino.dart';
import 'package:flutter_datetime_picker/flutter_datetime_picker.dart';

class CustomPicker extends CommonPickerModel {
  CustomPicker({DateTime? currentTime})
      : super(locale: 'en', currentTime: currentTime) {
    // TODO: implement CustomPicker
this.currentTime = currentTime ?? DateTime.now();
    this.setLeftIndex(this.currentTime.year - 2000);
    this.setMiddleIndex(this.currentTime.month - 1);
    this.setRightIndex(this.currentTime.day - 1);
  }
  
    throw UnimplementedError();
  }

  get selecteds => null;

  @override
  String leftStringAtIndex(int index) {
    if (index >= 0 && index < 100) {
      return '${index + 2000}';
    } else {
      return '';
    }
  }

  @override
  String middleStringAtIndex(int index) {
    if (index >= 0 && index < 12) {
      return '${index + 1}';
    } else {
      return '';
    }
  }

  @override
  String rightStringAtIndex(int index) {
    if (index >= 0 && index < 31) {
      return '${index + 1}';
    } else {
      return '';
    }
  }

  @override
  String leftDivider() {
    return '|';
  }

  @override
  String rightDivider() {
    return '|';
  }

  @override
  List<int> layoutProportions() {
    return [1, 1, 1];
  }

  @override
  DateTime finalTime() {
    return currentTime.isUtc
        ? DateTime.utc(leftStringAtIndex(selecteds[0])! as int,
        middleStringAtIndex(selecteds[1])! as int,
        rightStringAtIndex(selecteds[2])! as int,
        currentTime.hour,
        currentTime.minute,
        currentTime.second)
        : DateTime(
        leftStringAtIndex(selecteds[0])! as int,
        middleStringAtIndex(selecteds[1])! as int,
        rightStringAtIndex(selecteds[2])! as int,
        currentTime.hour,
        currentTime.minute,
        currentTime.second);
  }
}