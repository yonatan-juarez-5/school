# Makefile for CS 455 extra credit assgt 
#
#
#     make ectest
#        Makes ectest executable
#
#     make clean
#        Removes out .o files and executables
#


CXXFLAGS = -ggdb -Wall -std=c++11
OBJS = ecListFuncs.o ectest.o


ectest: $(OBJS)
	$(CXX) $(CXXFLAGS) $(OBJS) -o ectest

$(OBJS): ecListFuncs.h

clean:
	-rm -f ectest $(OBJS)
