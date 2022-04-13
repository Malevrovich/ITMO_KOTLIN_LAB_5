package share.commands

import share.commands.util.CommandResult
import share.commands.util.CommandType

class HelpCmd: Command("help", CommandType.HELP) {
    override fun execute(): CommandResult {
        return CommandResult(false, "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "save : сохранить коллекцию в файл\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (без сохранения в файл)\n" +
                "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "history : вывести последние 8 команд (без их аргументов)\n" +
                "sum_of_length : вывести сумму значений поля length для всех элементов коллекции\n" +
                "print_unique_genre : вывести уникальные значения поля genre всех элементов в коллекции\n" +
                "print_field_descending_screenwriter : вывести значения поля screenwriter всех элементов в порядке убывания")
    }
}