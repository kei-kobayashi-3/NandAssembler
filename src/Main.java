import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) throws IOException {
    // Parserコンストラクタ
    Parser paser = new Parser(
        new File("../nand2tetris/projects/06/rect/RectL.asm"));

    FileWriter fw = new FileWriter(
        new File("../nand2tetris/projects/06/rect/RectL.hack"));
    BufferedWriter bw = new BufferedWriter(fw);

    while (paser.hasMoreCommands()) {
      paser.advance();
      String commandType = paser.getCommandType();
      String output = null;

      if (!("C_COMMAND".equals(commandType))) {
        int symNum = Integer.parseInt(paser.symbol());
        output = String.format("%16s", Integer.toBinaryString(symNum)).replace(" ", "0");

      } else {
        int comp_val = Code.comp(paser.comp());
        int dest_val = Code.dest(paser.dest());
        int jump_val = Code.jump(paser.jump());

        String comp_str = String.format("%7s", Integer.toBinaryString(comp_val)).replace(" ", "0");
        String dest_str = String.format("%3s", Integer.toBinaryString(dest_val)).replace(" ", "0");
        String jump_str = String.format("%3s", Integer.toBinaryString(jump_val)).replace(" ", "0");
        output = String.format("111%7s%3s%3s", comp_str, dest_str, jump_str);
      }
      bw.write(output);
      bw.newLine();
    }
    bw.flush();
    bw.close();
  }

}
