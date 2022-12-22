/*  Name: Yonatan Juarez
 *  USC NetID: 9778 5289 75
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

  int count = 0;
  int temp = 0;
  bool firstDup = false;
  if(list != NULL){
     temp = list->data;
     list = list->next;
  }
  while(list != NULL){
     if(list->data == temp){
        if(!firstDup){
           count++;
           firstDup = true;
        }
        
     }
     else{
        
        firstDup = false;
        
     }
     temp = list->data;
     list = list->next;
     
        
  }
  return count;
}


void removeDiv(ListType & list, int k) {
    
   while(list != NULL && list->data %k == 0){
       list = list->next;
       
    }
    ListType newList = NULL;
    ListType current = list; 
    while(current!=NULL){
        if(current->data % k == 0 ){
            newList->next = current->next;
        }
        else{
            newList = current;
            
        }
        current = current->next;

    }
    

}



void splitAtLoc(ListType &list, int index, ListType &a, ListType &b) {
   ListType temp = list;
   int count = 1;
   if(list == NULL )
   {
       a = NULL;
       b = NULL;
       return;
   }
   
   if(index == 0){
      a = NULL;
      b = list->next;
      
   }
   else if(index < 0){
      a = NULL;
      b = list;
   }
   else{
   
      while(count < index){
         
         list = list->next;
         count++;
      }
      
      a = list;
      if(list ->next != NULL){
         list=list->next;
         b = list->next;
         
      }
      else{
         b = NULL;
      }
      a ->next = NULL;
      a = temp;
   }
   
   list = NULL;
   
}


