#!/usr/bin/env python3

import sys, os

info = ('Netshare  Copyright (C) 2019  Saulo G. Felix\n'
       'This program comes with ABSOLUTELY NO WARRANTY.\n'
       'This is free software, and you are welcome to redistribute it.\n')

def main_menu():
    print(info)
    print("1. Compile & execute")
    print("2. Execute")
    print("3. Print modules")
    print("4. Update")
    print("5. Debug")

    print("\n0. Exit")
    choice = input(">> ")
    exec_menu(choice)
    return

def exec_menu(choice):
    ch = choice.lower()
    if ch == '':
        menu_actions['main_menu']()
    else:
        try:
            menu_actions[ch]()
        except KeyError:
            print("Builder: Invalid selection, please try again.\n")
    return

def execute():
    print('Builder: Executing application...')
    os.system('java -Xms250M -cp .:$MYSQL:build/bin --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml com.sys.Main')
    exit_program()

def compile_execute():
    print("Builder: Compiling...")
    os.system('javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml @build/source/src -d build/bin')
    execute()

def print_modules():
    os.system('cat build/source/src')

def update():
    os.system('find src/ -type f -name *.java > build/source/src')

def debug():
    os.system('javac -verbose --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml @build/source/src -d build/bin')
    execute()

def exit_program():
    sys.exit(0)

menu_actions = {
    '1': compile_execute,
    '2': execute,
    '3': print_modules,
    '4': update,
    '0': exit_program,
}

if __name__ == "__main__":
    main_menu()
