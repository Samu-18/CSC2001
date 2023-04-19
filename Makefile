JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<
CLASSES=BinaryTreeNode.class BTQueueNode.class BTQueue.class BinaryTree.class\
 BinarySearchTree.class Post.class Account.class WeConnectApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class

run:
	java -cp bin WeConnectApp
doc:
	javacdoc -sourcepath ./src -d ./docs -subpackages .


