// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI 455 PA5
// Fall 2022


#include <iostream>

#include <cassert>

#include "listFuncs.h"

using namespace std;

Node::Node(const string &theKey, int theValue) {
   key = theKey;
   value = theValue;
   next = NULL;
}

Node::Node(const string &theKey, int theValue, Node *n) {
   key = theKey;
   value = theValue;
   next = n;
}

//function to remove target, return true if successful
//return false if target does not exist
bool listRemove(ListType & list, string target){
    ListType current = list;
    ListType tempPtr = NULL;

    while(current != NULL){
        if(current->key == target){
            if(tempPtr == NULL)
                list= list->next;
            else{
                tempPtr -> next = current->next;
                delete current;
            }
            return true;
        }
        tempPtr = current;
        current = current->next;
    }
   return false;
}

//add str:value pair to the list, return true if successful
//false if already present
bool listAdd(ListType& list, string str, unsigned int value){
    //if str is already in list, return false
    if(listContains(list, str) == true)
        return false;

    list = new Node(str, value, list);
    return true;
}

//check if target exists in list, if it doesn't return false
bool listContains(ListType& list, string target){
    ListType current = list;
    //loop through list to check if target exists
    while(current != NULL){
        if(current->key == target)
            return true;
        current = current->next;
    }
    //target not found
    return false;

}

//function will return the size of the list
unsigned int listSize(ListType& list){
    int size = 0;
    ListType current = list;

    while(current != NULL){
        size++;
        current= current->next;
    }
    return size;
}


//*************************************************************************
// put the function definitions for your list functions below

