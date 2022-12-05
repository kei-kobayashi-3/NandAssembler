import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {

    System.out.println("*/nand2tetris/projects/06/フォルダの絶対パスを入力して下さい。");
    Scanner sc = new Scanner(System.in);
    String input = sc.nextLine();
    String fileName = "../nand2tetris/projects/06/" + input;
    File file_a = new File(input);
    File file_b = new File(fileName);
    if (file_a.exists()) {
      fileName = input;
    } else if (file_b.exists()) {
    } else {
      System.err.println("ファイルが存在しません。");
      System.exit(0);
    }
    // Parserコンストラクタ
    Parser paser = new Parser(new File(fileName));
    int idx_path = fileName.lastIndexOf("/");
    int idx_fileName = fileName.lastIndexOf(".");
    String preStr = fileName.substring(0, idx_path);
    String outputFile = preStr + "/" + fileName.substring(idx_path, idx_fileName) + ".hack";
    FileWriter fw = new FileWriter(new File(outputFile));
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
        // もしシンボルテーブルに該当のシンボルがなければlinkedListに加える
        if (!symtbl.contains(paser.symbol())) {
          comList.add(paser.symbol());
        }

      } else {
        // LinkedListが空でない→シンボルテーブルにない→シンボルとその時のROMアドレスを加える
        // この時だけROMアドレスを増やす
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

    // Parserのリストに保存されているコマンドを最初から取り出す
    paser.setDataNum(0);
    // 2回目
    int ramNum = 16; // 変数のRAMアドレス
    while (paser.hasMoreCommands()) {
      paser.advance();
      String commandType = paser.getCommandType();
      String output = null;

      if (("A_COMMAND".equals(commandType))) {
        String symStr = paser.symbol();
        int symNum = -1;
        boolean isNumeric = symStr.matches("^\\d.*"); // 最初の文字が数字なら定数
        if (isNumeric) {
          symNum = Integer.parseInt(symStr);
        } else {
          // SymbolTableにあるならその値をoutput,ない場合はRamAddressをoutputしてSymbolTableに加え、RamAdressを増やす
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
    System.out.println("下記のパスにファイルを作成しました。");
    System.out.println(outputFile);
    bw.flush();
    bw.close();
  }
}
