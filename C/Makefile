CPPFLAGS=-g -Wall 

BOOST_HOME=/home/wanggang/boost_home
TNT_HOME=/home/wanggang/develop/cpp/tnt_126
JAMA_HOME=/home/wanggang/develop/cpp/jama125

LIBS= -L /usr/include/
INCLUDE = -I ${TNT_HOME} -I ${JAMA_HOME}

CPLUS_INCLUDE_PATH=${BOOST_HOME}/include
export CPLUS_INCLUDE_PATH

.PHONY : clean all

all: $(subst .cpp,.o,$(SOURCES))  pca


%.O: %.cpp
	$(CXX) $(CPPFLAGS) ${INCLUDE} ${LIBS} $^ $@
pca: pca.cpp
	$(CXX) $(CPPFLAGS) ${INCLUDE} $^  ${LIBS} -o $@

clean:
	rm -rf  *.o  pca
