import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import '../QuizApiLink/Question.dart';
import 'QuizManager.dart';

class AddQuizPage extends StatefulWidget {
  final String selectedCategory;
  final Function addQuiz;

  AddQuizPage({required this.selectedCategory, required this.addQuiz});

  @override
  _AddQuizPageState createState() => _AddQuizPageState();
}

class _AddQuizPageState extends State<AddQuizPage> {
  TextEditingController questionController = TextEditingController();
  TextEditingController option1Controller = TextEditingController();
  TextEditingController option2Controller = TextEditingController();
  TextEditingController option3Controller = TextEditingController();
  TextEditingController option4Controller = TextEditingController();
  TextEditingController correctAnswerController = TextEditingController();
  TextEditingController statement1Controller = TextEditingController();

  String? correctAnswer;
  PickedFile? _selectedImage;
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  bool noOptions = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'New Quiz Details',
          style: TextStyle(
            color: Colors.blue,
          ),
        ),
        backgroundColor: Colors.transparent,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                _buildCategoryText(),
                SizedBox(height: 20),
                _buildInputField(controller: questionController, label: 'Question', enabled: true),
                SizedBox(height: 10),
                _buildSelectedImage(),
                _buildAddImageButton(),
                _buildInputField(controller: option1Controller, label: 'Option 1', enabled: !noOptions),
                _buildInputField(controller: option2Controller, label: 'Option 2', enabled: !noOptions),
                _buildInputField(controller: option3Controller, label: 'Option 3', enabled: !noOptions),
                _buildInputField(controller: option4Controller, label: 'Option 4', enabled: !noOptions),
                SizedBox(height: 10),
                _buildNoOptionsCheckbox(),
                _buildCorrectAnswerInput(),
                SizedBox(height: 30),
                _buildActionButtons(),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildCategoryText() {
    return Text(
      'Selected Category: ${widget.selectedCategory}',
      style: TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.bold,
      ),
    );
  }

  Widget _buildInputField({
    required TextEditingController controller,
    required String label,
    required bool enabled,
  }) {
    return Padding(
      padding: EdgeInsets.symmetric(vertical: 10),
      child: TextFormField(
        controller: controller,
        enabled: enabled,
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
          ),
        ),
        style: TextStyle(
          fontSize: 16,
        ),
        validator: (value) {
          if (!noOptions && (value == null || value.isEmpty)) {
            return 'This field is required';
          }
          return null;
        },
      ),
    );
  }

  Widget _buildNoOptionsCheckbox() {
    return Row(
      children: [
        Checkbox(
          value: noOptions,
          onChanged: _handleNoOptionsChanged,
        ),
        Text('No options, enter the correct answer directly'),
      ],
    );
  }

  Widget _buildCorrectAnswerInput() {
    return noOptions
        ? _buildInputField(
      controller: statement1Controller,
      label: 'Correct Answer',
      enabled: true,
    )
        : _buildDropdownCorrectAnswer();
  }

  Widget _buildDropdownCorrectAnswer() {
    return DropdownButtonFormField<String>(
      value: correctAnswer,
      onChanged: (String? newValue) {
        setState(() {
          correctAnswer = newValue;
        });
      },
      items: <String>[
        'Option 1',
        'Option 2',
        'Option 3',
        'Option 4',
      ].map<DropdownMenuItem<String>>((String value) {
        return DropdownMenuItem<String>(
          value: value,
          child: Text(value),
        );
      }).toList(),
      decoration: InputDecoration(
        labelText: 'Correct Answer',
        border: OutlineInputBorder(),
      ),
    );
  }

  Widget _buildSelectedImage() {
    return _selectedImage != null
        ? Image.file(File(_selectedImage!.path), height: 100)
        : Container();
  }

  Widget _buildAddImageButton() {
    return TextButton.icon(
      onPressed: _pickImage,
      icon: Icon(Icons.image),
      label: Text('Add Image'),
    );
  }

  Widget _buildActionButtons() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: [
        SizedBox(
          width: 150,
          height: 60,
          child: ElevatedButton(
            onPressed: () {
              Navigator.pop(context); // Go back to the previous screen
            },
            child: Text(
              'Cancel',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            style: ElevatedButton.styleFrom(
              primary: Colors.red,
              padding: EdgeInsets.symmetric(
                horizontal: 40,
                vertical: 12,
              ),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),
        ),
        SizedBox(
          width: 180,
          height: 60,
          child: ElevatedButton(
            onPressed: _addQuiz,
            child: Text(
              'Add Quiz',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            style: ElevatedButton.styleFrom(
              primary: Colors.green,
              padding: EdgeInsets.symmetric(
                horizontal: 40,
                vertical: 12,
              ),
              shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),
        ),
      ],
    );
  }

  void _handleNoOptionsChanged(bool? newValue) {
    if (newValue != null) {
      setState(() {
        noOptions = newValue;

        if (noOptions) {
          option1Controller.clear();
          option2Controller.clear();
          option3Controller.clear();
          option4Controller.clear();
        }
      });
    }
  }

  Future<void> _pickImage() async {
    final pickedImage = await ImagePicker().getImage(source: ImageSource.gallery);
    if (pickedImage != null) {
      setState(() {
        _selectedImage = pickedImage;
      });
    }
  }

  void _addQuiz() async {
    if (_formKey.currentState!.validate()) {
      // Create a Question object based on user input
      final newQuestion = _createQuestionFromInput();

      try {
        await QuizManager().addQuiz(newQuestion);
        print('Quiz added successfully');
        Navigator.pop(context); // Go back to the previous screen
      } catch (error) {
        print('Error1 adding quiz: $error');
      }
    }
  }

  Question _createQuestionFromInput() {
    final isOption = noOptions ? 1 : 0;
    final questionText = noOptions ? questionController.text : '';

    int answerValue = 0; // Default value for answer

    if (!noOptions) {
      // Ensure that correctAnswer is not null before using it
      if (correctAnswer != null) {
        // If options are provided, determine the answer based on the selected option
        switch (correctAnswer) {
          case 'Option 1':
            answerValue = 1;
            break;
          case 'Option 2':
            answerValue = 2;
            break;
          case 'Option 3':
            answerValue = 3;
            break;
          case 'Option 4':
            answerValue = 4;
            break;
          default:
            answerValue = 0; // Default to 0 if no correct answer is selected
        }
      }
    }

      return Question(
      category: widget.selectedCategory.toUpperCase(),
      question: questionController.text,
      option1: noOptions ? null : option1Controller.text,
      option2: noOptions ? null : option2Controller.text,
      option3: noOptions ? null : option3Controller.text,
      option4: noOptions ? null : option4Controller.text,
      answer: answerValue,
      isOption: isOption,
      statement1: noOptions ? statement1Controller.text : null,
      statement2: noOptions ? statement1Controller.text : null, // Set this to null or another appropriate value
      statement3: noOptions ? statement1Controller.text : null, // Set this to null or another appropriate value
      image: 'assets/icons/picturelearning.png', // Set this to null or another appropriate value
    );
  }

}
