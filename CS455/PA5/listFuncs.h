// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI 455 PA5
// Fall 2022


//*************************************************************************
// Node class definition
// and declarations for functions on ListType

// Note: we don't need Node in Table.h
// because it's used by the Table class; not by any Table client code.

// Note2: it's good practice to *not* put "using" statement in *header* files.  Thus
// here, things from std libary appear as, for example, std::string

#ifndef LIST_FUNCS_H
#define LIST_FUNCS_H

#include <string>


struct Node {
   std::string key;
   int value;

   Node *next;

   Node(const std::string &theKey, int theValue);

   Node(const std::string &theKey, int theValue, Node *n);
};


typedef Node * ListType;

//*************************************************************************
//add function headers (aka, function prototypes) for your functions
//that operate on a list here (i.e., each includes a parameter of type
//ListType or ListType&).  No function definitions go in this file.

//function to remove target, return true if successful
//return false if target does not exist
bool listRemove(ListType & list,  std::string  target);
//add str:value pair to the list, return true if successful
//false if already present
bool listAdd(ListType& list, std::string str, unsigned int value);
//check if target exists in list, if it doesn't return false
bool listContains(ListType& list, std::string target);
//function will return the size of the list
unsigned int listSize(ListType& list);



// keep the following line at the end of the file
#endif

