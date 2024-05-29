// ecListFuncs.h
// CSCI 455 Fall 2022, Extra Credit assignment
// You do not need to modify or submit this file.

#ifndef EC_LIST_FUNCS_H
#define EC_LIST_FUNCS_H

#include <iostream>
#include <string>


//*************************************************************************
// Node type used for lists
struct Node {
   int data;
   Node *next;

   Node(int item);
   Node(int item, Node *n);

};


typedef Node * ListType;


//*************************************************************************
// Functions you need to write for this assignment:
//   (implementations go in ecListFuncs.cpp)


/*
 * numAdjDupes
 *
 * PRE: list is a well-formed list.
 *
 * returns the number of sequences of adjacent duplicate values in a list
 *
 *
 * Examples:
 *  list                     numAdjDupes(list)
 *    ()                     0
 *    (2 8 3)                0
 *    (4 4 7 3)              1
 *    (5 5 7 5 5 5 5)        2
 *    (5 7 5 7)              0
 *    (5 5 5 3 3 3 4 4 4 4)  3
 *  
 */
int numAdjDupes(ListType list);



/*
 * removeDiv
 *
 * PRE: list is a well-formed list and k >= 1
 * 
 * removes all elements divisible by k from the list
 *
 * Examples:
 *
 *  list before     k     list
 *  before call:          after call to removeDiv(list, k):
 *    ()            3     ()
 *    (7 10)        3     (7 10)
 *    (24 12 6 9)   4     (6 9)
 *    (24 12 6 9)   3     ()
 *    (3 2 8 4 7)   2     (3 7)
 *    (1 2 3 4 5)   1     ()
 */
void removeDiv(ListType & list, int k);



/*
 * splitAtLoc
 *
 * PRE: list is a well-formed list
 *
 * Assuming nodes are numbered starting from 0, splits list into two sub-lists as follows: 
 * "a" will contain all the elements up to, but not including, the node at the given location
 * from the original list.  And "b" will contain all the elements after
 * the node at the given location in the original list.  Otherwise the values in the new
 * lists will be in the same order as they were in the original list.  
 * If location >= the length of the list, all the elements will be in "a",
 * and "b" will be NULL; likewise, if location < 0, all the elements will be in "b", 
 * and "a" will be NULL.
 * After the operation, list will have the value NULL (the function destroys the list, because
 * it reuses nodes form the original list).
 *
 * NOTE: this function does not create any nodes, but reuses most or all of the nodes from 
 * the original list.
 *
 * Examples (list' indicates the value of list after the call):
 *
 *  list        loc           a         b           list'
 *  (7 4 4 3 9)  2            (7 4)     (3 9)       ()
 *  (7 4 2 3 9)  0            ()        (4 2 3 9)   ()
 *  (1 2 3 3 2)  4            (1 2 3 3) ()          ()
 *  ()           3            ()        ()          ()
 *  (8 2 5)      2            (8 2)     ()          ()
 *  (8 2 5)      3            (8 2 5)   ()          ()
 *  (8 2 5)     -3            ()        (8 2 5)     ()
 *  (3)          0            ()        ()          ()
 *  (3 5)        0            ()        (5)         ()
 *  (3 5)        1            (3)       ()          ()
 *
 */
void splitAtLoc(ListType &list, int loc, ListType &a, ListType &b);

//*************************************************************************

#endif
