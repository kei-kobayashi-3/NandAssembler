import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;

/**
 * Parser
 */
public class Parser {

  private ArrayList<String> commandsList = new ArrayList<>();
  private int data_num = 0;
  private final String a_command = "A_COMMAND";
  private final String c_command = "C_COMMAND";
  private final String l_command = "L_COMMAND";

  private String command = null;
  private String commandType = null;

  // getter, setter

  public void setDataNum() {
    data_num = 0;
  }

  public ArrayList<String> getCommandList() {
    return commandsList;
  }

  public String getCommand() {
    return command;
  }

  public String getCommandType() {
    return commandType;
  }

  // コンストラクタ
  public Parser(File file) {

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String text = "";
      while ((text = br.readLine()) != null) {
        // コメントアウト以降削除
        text = text.replaceAll("//.*", "");
        // 空白文字除去
        text = text.replaceAll("[\s\r\n]+", "");
        if (!(text == null || text.isEmpty())) {
          commandsList.add(text);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Boolean hasMoreCommands() {
    return data_num < commandsList.size();
  }

  // enable if hasMoreCommand is true
  public void advance() {
    if (hasMoreCommands()) {
      command = commandsList.get(data_num);
      commandType();
      data_num++;
    } else {
      System.err.println("パースするコマンドはありません");
      ;
    }
  }

  private void commandType() {
    if (command.contains("@")) {
      commandType = a_command;
    } else if (command.contains("(")) {
      commandType = l_command;
    } else {
      commandType = c_command;
    }
  }

  // enable when a or l command
  public String symbol() {
    String str = command.replaceAll("[@()]+", "");
    return str;
  }

  // enable when c_command
  public String dest() {
    String str = null;
    if (command.contains("=")) {
      str = command.substring(0, command.indexOf("="));
    }
    return str;
  }

  // enable when c_command
  public String comp() {
    int index_equal = command.indexOf("=");
    int index_semi = command.indexOf(";");
    String str = null;

    if (index_equal != -1 && index_semi != -1) {
      str = command.substring(index_equal + 1, index_semi);
    } else if (index_equal != -1) {
      str = command.substring(index_equal + 1);
    } else if (index_semi != -1) {
      str = command.substring(0, index_semi);
    } else {
      System.err.println("c_commandが不正です。");
    }
    return str;
  }

  // enable when c_command
  public String jump() {
    String str = null;
    int indexOfSemi = command.indexOf(";");
    if (indexOfSemi != -1) {
      str = command.substring(indexOfSemi + 1);
    }
    return str;
  }

}
