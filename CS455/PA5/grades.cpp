// Name: Yonatan Juarez
// USC NetID: 9778 5289 75
// CSCI 455 PA5
// Fall 2022

/*
 * grades.cpp
 * A program to test the Table class.
 * How to run it:
 *      grades [hashSize]
 * 
 * the optional argument hashSize is the size of hash table to use.
 * if it's not given, the program uses default size (Table::HASH_SIZE)
 *
 */

#include "Table.h"

// cstdlib needed for call to atoi
#include <cstdlib>

using namespace std;

//method headers that will be used for each cmd available

void insertNameScore(Table *&grades, string name, int score);
void changeScore(Table *& grades, string name, int score);
void lookupName(Table *&grades, string name);
void removeName(Table *&grades, string name);

void size(Table *&grades);
void stats(Table *&grades);
void help();
void quit();
void mainHelper(Table *&grades);

int main(int argc, char * argv[]) {

   Table * grades;  // Table is dynamically allocated below, so we can call
                     // different constructors depending on input from the user.

   // optionally gets the hash table size from the command line
   if (argc > 1) {
      int hashSize = atoi(argv[1]);  // atoi converts c-string to int

      if (hashSize < 1) {
         cout << "Command line argument (hashSize) must be a positive number"
              << endl;
         return 1;
      }

      grades = new Table(hashSize);

   }
   else {   // no command line args given -- use default table size
      grades = new Table();
   }

   grades->hashStats(cout);

   // add more code here
   // Reminder: use -> when calling Table methods, since grades is type Table*
   mainHelper(grades);

   return 0;
}

//mainHelper() will launch the grades program and allow user to
//enter different commands method will take in the input and
//perform the appropriate actions
void mainHelper(Table *&grades){
    string input = "";
    int score = 0;
    string name = "";
    while(true){
        cout << "cmd> ";
        cin >> input;

        if(input == "insert"){
            cin >> name;
            cin >> score;
            insertNameScore(grades, name, score);
        }
        else if(input == "change"){
            cin >> name;
            cin >> score;
            changeScore(grades, name, score);
        }
        else if(input == "lookup"){
            cin >> name;
            lookupName(grades, name);
        }

        else if(input == "remove") {
           cin >> name;
           removeName(grades,name);
        }

        else if(input == "print"){ grades->printAll();}

        else if(input == "size"){ size(grades);}
        else if(input == "stats"){ stats(grades);}

        else if(input == "help"){ help();}


        else if (input == "quit"){
            //quit();
            break;
        }
        else{
            cout << "ERROR: invalid command.\n";
            help();
        }
    }
}

void insertNameScore(Table *&grades, string name, int score){
    if(grades->insert(name, score) == true){
        //cout << "insert done.\n";
    }
    else{
        cout << "\'" << name << "\' is already present in table.\n";
    }
}
void changeScore(Table *& grades, string name, int score){
    if(grades->lookup(name) != NULL){
        int *scorePtr = grades->lookup(name);
        int temp = *scorePtr;
        *scorePtr = score;
        cout << "\'" << name << "\' score has been updated from "<< temp << " to " << *grades->lookup(name) << endl;
    }
    else{
        cout << "\'" << name << "\' is not present in the grades\' table.\n";
    }
}
void lookupName(Table *&grades, string name){
    if(grades->lookup(name) != NULL)
        cout << "\'" << name << "\': " << grades->lookup(name) << endl;

    else
        cout << "\'" << name << "\' is not present in the table.\n";
}

void removeName(Table *&grades, string name){
    if(grades->remove(name) == true)
        cout << "\'" << name << "\' has been removed from the table.\n";
    else
        cout << "\'" << name << "\' cannot be removed because it does not exist in the table.\n";
}

void size(Table *&grades){
    cout << "Table size: " << grades->numEntries() << endl;
}
void stats(Table *&grades){
    grades->hashStats(cout);
}
void help(){
    cout << "*Valid commands to use listed below*\n";
    cout << "\'insert name score\' to insert name and score\n";
    cout << "\'change name newscore\' to updated score (if name exists)\n";
    cout << "\'loopkup name\' to lookup name and print score (if present)\n";
    cout << "\'remove name\' to remove name (if exists)\n";
    cout << "\'print\' to print out all names and scores in the table\n";
    cout << "\'size\' prints out the number of entries in the table\n";
    cout << "\'stats\' prints out stats about the hash table at this point\n";
    cout << "\'help\' prints out a brief command summary\n";
    cout << "\'quit\' exists the program\n";
}
void quit(){
    cout << "Exiting program!\n";
}