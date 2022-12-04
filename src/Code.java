public class Code {

  public static int dest(String mnemonic) {
    int num = 0;
    if (mnemonic == null) {
      return num;
    }
    switch (mnemonic) {
      case "M":
        num = 1;
        break;
      case "D":
        num = 2;
        break;
      case "MD":
        num = 3;
        break;
      case "A":
        num = 4;
        break;
      case "AM":
        num = 5;
        break;
      case "AD":
        num = 6;
        break;
      case "AMD":
        num = 7;
        break;
      default:
        System.out.println("destの変換に失敗しました");
        break;
    }
    return num;
  }

  public static int comp(String mnemonic) {
    int num = 0;
    switch (mnemonic) {
      case "0":
        num = 42;
        break;
      case "1":
        num = 63;
        break;
      case "-1":
        num = 58;
        break;
      case "D":
        num = 12;
        break;
      case "A":
        num = 48;
        break;
      case "!D":
        num = 13;
        break;
      case "!A":
        num = 49;
        break;
      case "-D":
        num = 15;
        break;
      case "-A":
        num = 51;
        break;
      case "D+1":
        num = 31;
        break;
      case "A+1":
        num = 55;
        break;
      case "D-1":
        num = 14;
        break;
      case "A-1":
        num = 50;
        break;
      case "D+A":
        num = 2;
        break;
      case "D-A":
        num = 19;
        break;
      case "A-D":
        num = 7;
        break;
      case "D&A":
        num = 0;
        break;
      case "D|A":
        num = 21;
        break;
      case "M":
        num = 112;
        break;
      case "!M":
        num = 113;
        break;
      case "-M":
        num = 115;
        break;
      case "M+1":
        num = 119;
        break;
      case "M-1":
        num = 114;
        break;
      case "D+M":
        num = 66;
        break;
      case "D-M":
        num = 83;
        break;
      case "M-D":
        num = 71;
        break;
      case "D&M":
        num = 64;
        break;
      case "D|M":
        num = 85;
        break;
      default:
        System.out.println("compの変換に失敗しました");
        break;
    }
    return num;
  }

  public static int jump(String mnemonic) {
    int num = 0;
    if (mnemonic == null) {
      return num;
    }
    switch (mnemonic) {
      case "JGT":
        num = 1;
        break;
      case "JEQ":
        num = 2;
        break;
      case "JGE":
        num = 3;
        break;
      case "JLT":
        num = 4;
        break;
      case "JNE":
        num = 5;
        break;
      case "JLE":
        num = 6;
        break;
      case "JMP":
        num = 7;
        break;
      default:
        System.out.println("jumpの変換に失敗しました");
        break;
    }
    return num;
  }
}
