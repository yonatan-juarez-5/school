/*  Name: Yonatan Juarez
 *  USC NetID: 9778 5289 75
 *  CS 455 Fall 2022
 *  Extra credit assignment
 *
 *  ectest.cpp
 *
 *  a non-interactive test program to test the functions described in ecListFuncs.h
 *
 *    to run it use the command:   ectest
 *
 *  Note: this uses separate compilation.  You put your list code ecListFuncs.cpp
 *  Code in this file should call those functions.
 */


#include <iostream>
#include <string>

// for istringstream used in buildList (defined below)
#include <sstream>

#include "ecListFuncs.h"

using namespace std;


// You may use the following two utility functions that will make it easier to
// test your list functions on hard-coded list data and compare it to expected
// output:
// (function definitions for them appear at the bottom of this file)


/*
 * listToString
 *
 * PRE: list is a well-formed list.
 *
 * converts the list to a string form that has the following format shown by example.
 * the list is unchanged by the function.
 *
 *   string format:
 *
 *   "()"        an empty list
 *   "(3)        a list with one element, 3
 *   "(3 4 5)"   a list with multiple elements: 3 followed by 4 followed by 5
 *
 */
string listToString(ListType list);


/*
 * buildList
 * 
 * PRE: listString only contains numbers (valid integer format) and spaces
 *
 * creates and returns a linked list from a string of space separated numbers
 * 
 *
 * Examples:
 *  listString         return value of buildList(listString)
 *
 *    ""               ()
 *    "-32"            (-32)
 *    "     -32   "    (-32)
 *    "1 3 2"          (1 3 2)
 *    "  1 3 2"        (1 3 2)
 *
 */
ListType buildList(const string & listString);

//3 methods I created to thoroughly test each method from eListFuncs.cpp
//I used several test cases and compare the result with a predetermined correct
//answer and compare them to find out if my functions return the correct value

void splitListTester();
void removeDivTester();
void adjDupTester();


int main ()
{
   cout << "Testing adjacent duplicates:";
   adjDupTester();
   
   cout << "\nTesting removing divisible element:";
   removeDivTester();
   
   cout << "\nTesting split at location:" << endl;
   splitListTester();
   
   
   return 0;
}

//helper function to test numAdjDupes()   
void adjDupTester(){
   string inputs[] = {"", "2 8 3", "4 4 7 3", "5 5 7 5 5 5 5", "5 7 5 7", "5 5 5 3 3 3 4 4 4 4"};
   int expected[] = {0,0,1,2,0,3};
   
   for(int i = 0; i< 6; i++){
      ListType list = buildList(inputs[i]);
      
      cout << "List: " <<listToString(list) << endl;
      cout << "Number Adjacent Duplicates: " << numAdjDupes(list) << endl;
      cout << "Expected: " << expected[i] << endl;
      
      if ( numAdjDupes(list)== expected[i])
         cout << "TRUE" <<endl;
      else
         cout << "FALSE" <<endl;
      
      cout <<endl;
   }
   
   
}
//helper function to test splitAtLoc()
void splitListTester(){
   string inputs[] = {"7 4 4 3 9", "7 4 4 3 9", "1 2 3 3 2 ", "8 2 5", "8 2 5"};
   string arrA[] = {"(7 4)", "()", "(1 2 3 3)", "(8 2)", "()"};
   string arrB[] = {"(3 9)", "(4 4 3 9)", "()", "()", "(8 2 5)"};
   int locations[]= {2,0,4,2, -3}; 
   
   for(int i = 0; i< 5; i++){
      ListType list = buildList(inputs[i]);
      ListType a, b;
      
      cout << "List: " <<listToString(list) << endl;
      cout << "Split at loc: " << locations[i] << endl;
      splitAtLoc(list, locations[i], a,b);
      cout << "a: "<< listToString(a) << endl;
      cout << "b: " << listToString(b) << endl;
      if (listToString(a) == arrA[i] && listToString(b) == arrB[i])
         cout << "TRUE" <<endl;
      else
         cout << "FALSE" <<endl;
      
      cout <<endl;
   }
   
   
}

//helper function to test removeDiv() 
void removeDivTester(){
   string inputs[] = {"", "7 10", "24 12 6 9", "24 12 6 9", "3 2 8 4 7", "1 2 3 4 5"};
   int k[]= {3, 3, 4,3,2,1}; 
   string expected[] = {"()", "(7 10)", "(6 9)", "()", "(3 7)", "()"};
   
   for(int i = 0; i< 6; i++){
      ListType list = buildList(inputs[i]);
      
      cout << "List: " <<listToString(list) << endl;
      cout << "Remove element divisible by: " << k[i] << endl;
      removeDiv(list, k[i]);
      cout << "Result: "<< listToString(list) << endl;
      cout << "Expected: " << expected[i] << endl;
      if (listToString(list) == expected[i])
         cout << "TRUE" <<endl;
      else
         cout << "FALSE" <<endl;
      
      cout <<endl;
   }
}

/*********************************************************
 * Utility function definitions
 *
 */
string listToString(ListType list) {

   string listString = "(";

   if (list == NULL) {
      listString += ")";
      return listString;
   }

   Node *p = list;
   while (p->next != NULL) {
      listString += to_string(p->data) + " ";
      p = p->next;
   }

   // print last one with no trailing space
   listString += to_string(p->data) + ")";

   return listString;

}   


ListType buildList(const string & listString) {

   ListType nums = NULL;

   istringstream istr(listString);  // similar to a Java Scanner over a String

   int num;

   if (istr >> num) { // is there one value there?
      nums = new Node(num);
   }
   else {
      return NULL;
   }

   Node *last = nums;

   while (istr >> num) { 
      last->next = new Node(num);
      last = last->next;
   }

   return nums;
}



