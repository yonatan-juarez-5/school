/*  Name:
 *  USC NetID:
 *  CS 455 Fall 2022
 *
 *  See ecListFuncs.h for specification of each function.
 */

#include <string>
#include <cassert>

#include "ecListFuncs.h"

using namespace std;

// *********************************************************
// Node constructors: do not change
Node::Node(int item) { 
   data = item;
   next = NULL;
}

Node::Node(int item, Node *n) {
   data = item;
   next = n;
}
// *********************************************************


int numAdjDupes(ListType list) {

  return 0;
}



void removeDiv(ListType & list, int k) {
    cout << "in" << endl;
    ListType newList = NULL;
    cout << "in" <<endl;
    ListType current = list;
    cout << "in " << endl;

    bool check = current!=NULL?true:false;
    cout << check << endl;
    /*
    while(1){
        cout << "* ";
        if(current->data % k == 0 ){
            newList ->next = current->next;
        }
        else{
            newList = current;
            current = current->next;
        }
        break;

    }*/
    while(current!= NULL){
        if(current->data % k ==0){
            if(newList == NULL){
                newList->next = current->next;
                delete current;
                current = list;
            }
            else{
                list = list->next;
                delete current;
                current = list;
            }
        }
        else{
            newList = current;
            current = current->next;
        }
    }

}



void splitAtLoc(ListType &list, int index, ListType &a, ListType &b) {

}

