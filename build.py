#!/usr/bin/env python3

import sys, os

menu_actions  = {}

# =======================
#     MENUS FUNCTIONS
# =======================

def main_menu():
    os.system('clear')

    print("TreeSGDB\n")
    print("1. Compilar e executar")
    print("2. Executar")
    print("\n0. Sair")
    choice = input(">> ")
    exec_menu(choice)

    return

def exec_menu(choice):
    os.system('clear')
    ch = choice.lower()
    if ch == '':
        menu_actions['main_menu']()
    else:
        try:
            menu_actions[ch]()
        except KeyError:
            print("Invalid selection, please try again.\n")
    return

def compile_execute():
    print("Compilando...")
    os.system('javac --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml @build/source -d build/bin')
    execute()
    return

def execute():
    print('Executando aplicação...')
    os.system('java -cp .:$MYSQL:build/bin --module-path $PATH_TO_FX --add-modules=javafx.controls,javafx.fxml com.sys.Main') 
    exit()
    return

def back():
    menu_actions['main_menu']()

# exit program
def exit():
    sys.exit()

# =======================
#    MENUS DEFINITIONS
# =======================

# Menu definition
menu_actions = {
    'main_menu': main_menu,
    '1': compile_execute,
    '2': execute,
    '9': back,
    '0': exit,
}

# =======================
#      MAIN PROGRAM
# =======================

# Main Program
if __name__ == "__main__":
    # Launch main menu
    main_menu()
