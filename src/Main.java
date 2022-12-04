import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Main {

  public static void main(String[] args) throws IOException {
    // Parserコンストラクタ
    Parser paser = new Parser(
        new File("../nand2tetris/projects/06/rect/Rect.asm"));

    FileWriter fw = new FileWriter(
        new File("../nand2tetris/projects/06/rect/Rect.hack"));
    BufferedWriter bw = new BufferedWriter(fw);

    // SymbolTableコンストラクタ
    SymbolTable symtbl = new SymbolTable();

    // Symbolを解決する
    int romNum = 0;
    LinkedList<String> comList = new LinkedList<>();

    // 1回目
    while (paser.hasMoreCommands()) {
      paser.advance();
      String commandType = paser.getCommandType();

      if ("L_COMMAND".equals(commandType)) {
        if (!symtbl.contains(paser.symbol())) {
          comList.add(paser.symbol());
        }

      } else {
        if (!comList.isEmpty()) {
          while (!comList.isEmpty()) {
            boolean isContain = symtbl.contains(comList.peek());
            if (!isContain) {
              symtbl.addEntry(comList.pop(), romNum);
            }
          }
        }
        romNum++;
      }
    }

    // データを最初から
    paser.setDataNum();
    // 2回目
    int ramNum = 16;
    while (paser.hasMoreCommands()) {
      paser.advance();
      String commandType = paser.getCommandType();
      String output = null;

      if (("A_COMMAND".equals(commandType))) {
        String symStr = paser.symbol();
        int symNum = -1;
        boolean isNumeric = symStr.matches("^\\d.*");
        if (isNumeric) {
          symNum = Integer.parseInt(symStr);
        } else {
          if (symtbl.contains(symStr)) {
            symNum = symtbl.getAdress(symStr);
          } else {
            symNum = ramNum;
            symtbl.addEntry(symStr, ramNum);
            ramNum++;
          }
          if (symNum == -1) {
            System.out.println("A-L Commandのテーブル変換に失敗しました。");
          }
        }
        output = String.format("%16s", Integer.toBinaryString(symNum)).replace(" ", "0");

      } else if ("C_COMMAND".equals(commandType)) {
        int comp_val = Code.comp(paser.comp());
        int dest_val = Code.dest(paser.dest());
        int jump_val = Code.jump(paser.jump());

        String comp_str = String.format("%7s", Integer.toBinaryString(comp_val)).replace(" ", "0");
        String dest_str = String.format("%3s", Integer.toBinaryString(dest_val)).replace(" ", "0");
        String jump_str = String.format("%3s", Integer.toBinaryString(jump_val)).replace(" ", "0");
        output = String.format("111%7s%3s%3s", comp_str, dest_str, jump_str);
      } else if ("L_COMMAND".equals(commandType)) {
        continue;
      } else {
        System.out.println("A-L Commandの変換に失敗しました。");
      }
      bw.write(output);
      bw.newLine();
    }
    bw.flush();
    bw.close();
  }
}
