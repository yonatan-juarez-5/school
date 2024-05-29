// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI 455 PA5
// Fall 2022

// Table.cpp  Table class implementation


#include "Table.h"

#include <iostream>
#include <string>
#include <cassert>

// for hash function called in private hashCode method defined below
#include <functional>

using namespace std;


// listFuncs.h has the definition of Node and its methods.  -- when
// you complete it it will also have the function prototypes for your
// list functions.  With this #include, you can use Node type (and
// Node*, and ListType), and call those list functions from inside
// your Table methods, below.

#include "listFuncs.h"


//*************************************************************************
// create an empty table, i.e., one where numEntries() is 0
   // (Underlying hash table is HASH_SIZE.)
Table::Table() {
    hashSize = HASH_SIZE;
    numberEntries = 0;
    hashTable = new ListType[hashSize];
}
// create an empty table, i.e., one where numEntries() is 0
// such that the underlying hash table is hSize
Table::Table(unsigned int hSize) {
    hashSize = hSize;
    numberEntries = 0;
    hashTable = new ListType[hashSize];
}
// returns the address of the value that goes with this key
   // or NULL if key is not present.
   //   Thus, this method can be used to either lookup the value or
   //   update the value that goes with this key.
int * Table::lookup(const string &key) {
   unsigned int code = hashCode(key);
   if(listContains(hashTable[code], key)){
        ListType current = hashTable[code];
        while(current->key != key){
            current = current->next;
        }
        return &(current->value);
   }
   return NULL;
}

// remove a pair given its key
   // return false iff key wasn't present
   //         (and no change made to table)
bool Table::remove(const string &key) {
   unsigned int code = hashCode(key);
   if(listRemove(hashTable[code], key) == true){
       numberEntries--;
       return true;
   }
   return false;
}

// insert a new pair into the table
   // return false iff this key was already present
   //         (and no change made to table)
bool Table::insert(const string &key, int value) {
   unsigned int code = hashCode(key);
   cout << code << endl;
   if(listAdd(hashTable[code], key, value) == true){
        numberEntries++;
        return true;
   }
   return false;
}

//returns the number of entries in the table
int Table::numEntries() const {
   return numberEntries;
}

//prints all the entries in table: name and score
void Table::printAll() const {

    for(int i = 0; i < hashSize; i++){
        if(hashTable[i] != NULL){
            Node* current = hashTable[i];
            while(current != NULL){
               cout << current -> key << " " << current->value << endl;
               current = current->next;
          }
        }
    }

}

// hashStats: for diagnostic purposes only
   // prints out info to let us know if we're getting a good distribution
   // of values in the hash table.
   // Sample output from this function
   //   number of buckets: 997
   //   number of entries: 10
   //   number of non-empty buckets: 9
   //   longest chain: 2
void Table::hashStats(ostream &out) const {
    out << "\tnumber of buckets: " << hashSize << endl;
    out << "\tnumber of entries: " << numberEntries << endl;
    out << "\tnumber of non-empty buckets: " << nonEmptyBuckets(hashTable) << endl;
    out << "\tlongest chain: " << longestChain(hashTable) << endl;
}


// hash function for a string
// (we defined it for you)
// returns a value in the range [0, hashSize)
unsigned int Table::hashCode(const string &word) const {

   // Note: calls a std library hash function for string (it uses the good hash
   //   algorithm for strings that we discussed in lecture).
   return hash<string>()(word) % hashSize;

}

//private helper methods

// add definitions for your private methods here

//longestChain() will return the longest chain in the list
int Table::longestChain(ListType *list) const{
   int chainLen = 0;
   for(int i = 0; i < hashSize; i++){
       if(list != NULL){
          if(listSize(list[i]) > chainLen)
             chainLen = listSize(list[i]);
       }
   }
   return chainLen;
}
//method will return the number on non empty buckets
int Table::nonEmptyBuckets(ListType *list) const{
    int count = 0;
    int hs = hashSize;
    for(int i= 0; i< hs; i++){
        if(list[i] != NULL)
            count++;
    }

    return count;
}