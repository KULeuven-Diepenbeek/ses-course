---
title: '5: Debugging'
pre: "<i class='far fa-keyboard'></i> "
draft: true
weight: 5
author: Wouter Groenveld
---

### Chapter 5.0

# Debugging in C

Important concepts to grasp:

- Breakpoints, stepping into/over, continuing
- Inspecting the stack and the heap
- Disassembling, objdumping


## Recommended Reading

- [The GNU Project Debugger Documentation](https://www.gnu.org/software/gdb/documentation/)
- [Hackme: exploiting heap bugs](https://tc.gtisc.gatech.edu/cs6265/2016/l/lab10-heap/README-tut.txt
)
- [Google Test Primer](https://github.com/google/googletest/blob/master/googletest/docs/primer.md)

## 5.1. The easy way: Debugging using an IDE


Arguably one of the better integrated C/C++ IDEs out there is [CLion](www.jetbrains.com/clion), a toolkit from Jetbrains based on the IDEA platform you all know from IntelliJ. It has exactly the same tools and capabilities but is fully geared towards C and C++. Cross-compiling and toolchain setup is also very easy using CLion. The Figure below is a screen capture of CLion showcasing it's integrated unit testing capabilities which we will expand upon in the coming sections. 

<img src="/img/clion.png" />

A quick glance at the screenshot reveals the following buttons:

- Play: Compile and Run
- Debug
- Attach to process
- Run tests (step through, ...)
- File management window
- Gutter with line numbers and possibility to add breakpoints
- ...

{{% notice note %}}
A short live demo of CLion's debugging capabilities is in order here.
{{% /notice %}}

CLion is not free but a 30-day trail is, and as a student you can apply for a one-year license for free using your student e-mail address. Bigger development environments like this are typically used when developing large applications with a lot of source and header files. In this course, we will not be needing that. That is why the usage of a tool like this is not needed for now. 

Instead of relying on visual debug tools like CLion, another 'hard-core' commandline alternative exists for Linux: `gdb` (The GNU debug tool). 

## 5.2 Test-Driven Development: Google Test

A concept you learned to love in the [Software Engineering Skills](https://kuleuven-diepenbeek.github.io/ses-course/tdd/) course. 

It's concepts and definitions will **not** be repeated here, but we will introduce **Google Test**, a unit testing framework for C/C++ that enables us to write tests to track down bugs and **reduce the amount of time needed dabbling in `gdb`**. That is one of the major advantages of using automated test frameworks.

{{% notice warning %}}
Google Test is a `C++` (11) framework, not a `C` framework! We will be using `g++` instead of `gcc` to compile everything. C++ files are suffixed with `.cpp` instead of `.c`.<br/>
Major differences between both languages exist but will not be needed to know all about in order to write a few simple tests.<br/><br/>
Since `g++` and the tool we need to build it, `cmake`, are not installed on the image by default, use `apt install g++ cmake` to download and install the toolchains.
{{% /notice %}}

### A. Installation

Most open source libraries require you to download the source code and compile it yourself. For Google Test, we will do exactly that, since we are learning how to work with compiling and making things anyway. We want to only compile _googletest_, and not _googlemock_ - both are part of the same repository. 

- Clone the github repository: [https://github.com/google/googletest/](https://github.com/google/googletest/). We want to build branch `v1.12.x` - the `master` branch is too unstable. Remember how to switch to that branch? Use `git branch -a` to see all branches, and `git checkout -b [name] remotes/origin/[name]` to check it out locally. Verify with `git branch`.
-  Create a builddir and navigate into it: `mkdir build`, `cd build`
-  Build Makefiles using Cmake: `cmake ./../`
-  Build binaries using make: `make`. 

More information about CMake can be found in [chapter 2.5: C Ecosystems](/ch2-c/ecosystems/#3-cmake).

If all goes according to plan, four libraries will have been created:

1. `libgtest.a`
2. `ligbtest_main.a`
3. `libgmock.a` (we won't use this)
4. `libgmock_main.a` (we won't use this)

In the subfolder `googletest/build/lib`. 

### B. Usage

Using the library is a matter of doing two things:

#### 1. Adding include folders

You will need a `main()` function to bootstrap the framework:

```c
// main.cpp
#include "gtest/gtest.h"

int main(int argc, char *argv[]) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
```

And another file where our tests reside:

```c
// test.cpp
#include "gtest/gtest.h"

int add(int one, int two) {
    return one + two;
}

TEST(AddTest, ShouldAddOneAndTo) {
    EXPECT_EQ(add(1, 2), 5);
}

TEST(AddTest, ShouldAlsoBeAbleToAddNegativeValues) {
    EXPECT_EQ(add(-1, -1), -2);
}
```

What's important here is the include that refers to a `gtest/gtest.h` file. The gtest directory resides in the include folder of your google test installation directory. That means somehow we have to educate the compiler on **where to look for the includes**! 

{{% notice note %}}
The `-I[directory]` (I = include) flag is used to tell `g++` where to look for includes.
{{% /notice %}}

#### 2. Linking with the compiled libraries


When running the binary `main()` method, Google Test will output a report of which test passed and which test failed:

<pre>
Wouters-MacBook-Air:unittest wgroenev$ ./cmake-build-debug/unittest
[==========] Running 2 tests from 2 test cases.
[----------] Global test environment set-up.
[----------] 1 test from SuiteName
[ RUN      ] SuiteName.TrueIsTrue
[       OK ] SuiteName.TrueIsTrue (0 ms)
[----------] 1 test from SuiteName (0 ms total)

[----------] 1 test from AddTest
[ RUN      ] AddTest.ShouldAddOneAndTo
/Users/wgroenev/CLionProjects/unittest/test.cpp:18: Failure
      Expected: add(1, 2)
      Which is: 3
To be equal to: 5
[  FAILED  ] AddTest.ShouldAddOneAndTo (0 ms)
[----------] 1 test from AddTest (0 ms total)

[----------] Global test environment tear-down
[==========] 2 tests from 2 test cases ran. (0 ms total)
[  PASSED  ] 1 test.
[  FAILED  ] 1 test, listed below:
[  FAILED  ] AddTest.ShouldAddOneAndTo

 1 FAILED TEST
</pre>

However, before being able to run everything, `InitGoogleTest()` is implemented somewhere in the libraries we just compiled. That means we need to tell the compiler to **link the Google Test libraries** to our own application. 

{{% notice note %}}
Add libraries as arguments to the compiler while linking. Remember to first use the `-c` flag, and afterwards link everything together.
{{% /notice %}}

Bringing everything together:

<pre>
Wouters-MacBook-Air:debugging wgroeneveld$ g++ -I$GTEST_DIR/googletest/include -c gtest-main.cpp
Wouters-MacBook-Air:debugging wgroeneveld$ g++ -I$GTEST_DIR/googletest/include -c gtest-tests.cpp
Wouters-MacBook-Air:debugging wgroeneveld$ g++ gtest-main.o gtest-tests.o $GTEST_DIR/build/lib/libgtest.a $GTEST_DIR/build/lib/libgtest_main.a  -lpthread
Wouters-MacBook-Air:debugging wgroeneveld$ ./a.out
[==========] Running 2 tests from 1 test case.
[----------] Global test environment set-up.
[----------] 2 tests from AddTest
[ RUN      ] AddTest.ShouldAddOneAndTo    
</pre>

As you can see, it can be handy to create a shell variable `$GTEST_DIR` that points to your own Google Test directory. To do that, edit the `.bashrc` file in your `~` (home) folder. Remember that files starting with a dot are hidden by default, so use the `-a` flag of the `ls` command. Add the line:

`export GTEST_DIR=/home/[user]/googletest/googletest`

And reopen all terminals. Verify the above using `echo $GTEST_DIR`, it should print out the path. 

If you are using a different shell, edit your shell's config file. If you have no idea which shell you're using, you're probably using Bash. Verify with `echo $SHELL` which will likely output `/bin/bash`.

{{% notice note %}}
The `-lpthread` linking flag tells the compiler to link the standard threading libraries along with anything else, that are needed by GTest internally. We will get back on these in [chapter 6](/ch6-tasks). <br/>
Without this flag, you will get the following errors: "ld returned 1 exit status, undefined reference to pthread_[fn]"
{{% /notice %}}

### C. 'Debugging' with GTest

Going back to the crackme implementation, a simplified method that verifies input is the following:

```c
int verify(char* pwd) {
    // return 1 if verified against a pre-determined password, 0 otherwise.
}
```

{{% notice task %}}
Write a set of tests for the above method - **BEFORE** implementing it yourself! Time to hone your TDD skills acquired from the course 'Software Engineering Skills'. Simply copy it into the test file, or include it from somewhere else.<br/> You should at least have the following edge cases:

- right password entered
- wrong password entered
- empty password (what about `NULL` or `""`?)

Use the GTest macro `EXPECT_TRUE` and `EXPECT_FALSE`. These correspond to JUnit's `AssertTrue()` and `AssertFalse()`.

Again, watch out with the order in which parameters should be passed (expected/actual)! See [Google Test Primer](https://github.com/google/googletest/blob/master/googletest/docs/primer.md).
{{% /notice %}}


## 5.3. The hard way: Command-line debugging using GDB

In order to fluently debug binary programs, they have to be compiled with the **debug flag**, `gcc -g`. This will add metadata to the binary file that gdb uses when disassembling and setting breakpoints. IDEs automatically add metadata like this when you press the "Debug" button on them, but since this is a command-line application, we need to do everything ourselves. 

### 5.3.1 With debug flags

Let's start with a heap-based application we would like to inspect:

```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

char password[] = "250382";

int main(int argc, char *argv[])
{
  int stackvar = 5;
  char *buf = (char *)malloc(100);
  char *secret = (char *)malloc(100);

  strcpy(secret, password);

  printf("Crackme! \n");
  printf("Password? ");

  scanf("%s", buf);

  if (!strcmp(buf, secret)) {
    printf("Password OK :)\n");
  } else {
    printf("Invalid Password! %s\n", buf);
  }

  return 0;
}
```

If the source code would not have been supplied, making an estimated guess against the password would take a (very) long time. (We naively assume here that the program has been compiled with debug information enabled).

Compile using `gcc -g hackme.c`. Take a look at the filesize - without flag:

<pre>
wouter@wouter-Latitude-7490:~/Development$ gcc hackme.c -o hackme.bin && ls -la | grep hackme.bin
-rwxr-xr-x  1 wouter wouter 8568 Jan  7 19:59 hackme.bin
</pre>

With flag:

<pre>
wouter@wouter-Latitude-7490:~/Development$ gcc hackme.c -g -o hackme.bin && ls -la | grep hackme.bin
-rwxr-xr-x  1 wouter wouter 11352 Jan  7 19:59 hackme.bin
</pre>

Star the `gdb` debugger using `gdb [binary]`. It will enter the interactive gdb shell, where you can set breakpoints, step through code, and have a chance at inspecting the heap, where we might attempt to figure out what's hidden in there. 

Things you need to know from the GDB debugger:

1. `r`: running the program (main() method execution)
2. `c`: continue after a breakpoint
3. `i`: inspect (`i r [regname]`: inspect register name)
4. `start` and `next` (shorthand `n`) or `step`: start stepping through the application.
5. `b *[addr]`: set breakpoint at certain function/line/`*`address ([see manual](https://visualgdb.com/gdbreference/commands/break)). Conditionals are possible, for instance: `break func if arg == 3`.
6. `delete`: deletes all breakpoints
7. `disassemble [fn]`: disassembles functionname (after running)
8. `x/[length][format] [address expr]`: inspect dynamic memory block ([see manual](http://visualgdb.com/gdbreference/commands/x))
9. `print x` (shorthand: `p`): print `var`, or `&var` address (Enalbe printing of addresses: `show print address`)
10. `info` (shorthand: `i`) address/line (fn) or `source`

{{% notice task %}}
Bootstrap gdb and step through the whole application. As soon as the stackvar has been evaluated, try to inspect the memory value using `x/d`. The address expression could be hexadecimal, or `&stackvar`. <br/>
How could you evaluate a heap variable using the x command? If you have the address, how do you pry out the value on the heap? 
{{% /notice %}}

More useful commands:

- Don't remember which breakpoints you've set? `i b`. (info breakpoints)
- Don't remember where you're at now? Inspect the stack: `bt` (backtrace), optionally appended with `full` that includes local variables. 
- Don't know which registers to inspect? `i r` ([see manual](https://sourceware.org/gdb/onlinedocs/gdb/Registers.html)). 

{{% notice task %}}
Can you spot the stack pointer and program counter? Can you see what happens to them when a function is called or an instruction is executed? Why do you think the PC doens't simply increment with the expected four bytes when instructing gdb to execute a line of code?
{{% /notice %}}


Do not forget that the expression that is printed out is the one to be evaluated after you enter the 'next' command. You can already inspect the stack variable address but it will contain junk:

<pre>
(gdb) start
Temporary breakpoint 1 at 0x7d9: file hackme.c, line 11.
Starting program: /home/wouter/Development/hackme.bin 

Temporary breakpoint 1, main (argc=1, argv=0x7fffffffdd68) at hackme.c:11
11    int stack = 5;
(gdb) x/d &stack
0x7fffffffdc6c: 21845
(gdb) next
12    char *buf = (char *)malloc(100);
(gdb) x/d &stack
0x7fffffffdc6c: 5
</pre>

Address `0x7fffffffdc6c` first contains 21845 - a coincidence that might have another value on your machine.

{{% notice task %}}
Bootstrap gdb, disassemble the `main` function, and set breakpoints after each `malloc()` call using `b *[address]`. You can check the return value, stored at the register eax, with `i r eax`.
{{% /notice %}}

How come something interesting is hidden in `eax` after calling `malloc()`? 

1. Because `eax` is the _return value_ register, or the **accumulator**. You should be familiar with it due to other Hardware-oriented courses.
2. Because [malloc returns a void pointer](https://www.man7.org/linux/man-pages/man3/malloc.3.html) - read the `man` pages carefully!
 

### 2.2 Without debug flags

Now try to 'hack' the password using gdb without the `-g` compiler flag. Imagine someone has put up a binary file on the internet and you managed to download it. No source code available, and no debug information compiled in. The gdb tool still works, disassembling still works, but method information is withheld. That means calling `start` and `next` will **not** reveal much-needed information about each statement, and we will have to figure it out ourselves by looking at the disassembly information. 

{{% notice task %}}
Try to disassemble again and look at the heap value of our secret. Notice that you will not be able to use something like `x [varname]` because of the lack of debug information! We will have to rely on breakpoints of address values from the disassembly. 
{{% /notice %}}

Remember to always run the program first before disassembling - otherwise address values will be way too low, and thus incorrect. `bt` does noet help us either here: _No symbol table info available_. 

When inspecting the return value of `eax`, gdb returns a **relative address** for our current program (8 BITS), while we need an **absolute** one (16 BITS) when using the x command to inspect the heap. Look at the disassembly info to prepend the right bits:

<pre>
---Type <return> to continue, or q <return> to quit---
   0x0000555555554844 <+122>:   mov    -0x8(%rbp),%rdx
   0x0000555555554848 <+126>:   mov    -0x10(%rbp),%rax
   0x000055555555484c <+130>:   mov    %rdx,%rsi
...
(gdb) b *0x00005555555547ea
Breakpoint 1 at 0x5555555547ea
(gdb) r
Starting program: /home/wouter/Development/osc-labs/solutions/debugging/a.out 

Breakpoint 1, 0x00005555555547ea in main ()
(gdb) i r eax
eax            0x55756260   1433756256
(gdb) x 0x55756260
0x55756260: Cannot access memory at address 0x55756260
(gdb) x 0x0000555555756260
0x555555756260: 0x00000000
</pre>

As you can see, `0x55756260` is an invalid memory address, but based on the disassembly info, we can deduce it is actually `0x0000555555756260` we need to look at. 

There's another way to pry out the return value of the last statement. The `finish` command executes until the current stack is popped off (that is, the function ends) and prints the return value. Set a breakpoint to just below `malloc()`, call `finish`, and the result is:

```
(gdb) finish
Run till exit from #0  __GI___libc_malloc (bytes=100) at ./malloc/malloc.c:3294
0x0000aaaaaaaa08f4 in main ()
Value returned is $1 = (void *) 0xaaaaaaab22a0
```

There's your address you can now inspect using `r 0xaaaaaaab22a0`. It'll likely still be `0x00000000`, so try to `step` and inspect until it contains the value you're interested in. 

Remember that `finish` here works because we breaked _inside_ `malloc()`, which then becomes the current stack. If you're still debugging in `main`, an error will appear, as there is nothing to `finish`: popping the stack would end the program. 

{{% notice note %}}
Registers are platform- and architecture-specific! In other words, the return value register `eax` is only available on x86_64 CPUs. If you're on a modern Mac with an ARM64, you'll have to check `info all-registers` and consult the [ARM Developer Documentation](https://developer.arm.com/documentation/102374/0101/Registers-in-AArch64---general-purpose-registers) to find the correct register.<br/>
The `pc` and `sp` registers are universal concepts.
{{% /notice %}}


## The (still) hard way: DDD, a UI on top of GDB

Instead of invoking `gdb`, one can also employ `ddd`. This is a crude UI on top of the gdb debugger, with multiple windows where the same commands can be entered as you have learned so far. However, ddd also allows you to visualize heap/stack variables while stepping through the application. The Figure below shows a screen-shot of a debug session of our hackme app using ddd. 

<img src="/img/ddd.png" />

Things to try out:

- Display the Source Window via the View menu. This window lets you set breakpoints and interact with the source code.
- Display the Machine Code Window via the View menu. This window is the equivalent of `bt` (`backtrace`) in `gdb`.
- Right-click on a line in source (compile with `-g` again!) -> Add breakpoint
- Start/step using the buttons or the commands in the cmdline window.
- Right-click in the main window -> "New Display" to add variables by name to watch (for instance `buf` and `password`, as shown). You can also watch references to functions - any valid `gdb`-style expression will do.

{{% notice task %}}
Take a moment to fiddle with `ddd` after correctly installing it. Try to inspect the same heap variable as the previous exercises, but this time visualize them in the main window. It should be (slightly) easier to accomplish.
{{% /notice %}}
