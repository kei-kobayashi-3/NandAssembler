import java.util.HashMap;

public class SymbolTable {

  private HashMap<String, Integer> hashMap = new HashMap<>();

  public SymbolTable() {
    hashMap.put("SP", 0);
    hashMap.put("LCL", 1);
    hashMap.put("ARG", 2);
    hashMap.put("THIS", 3);
    hashMap.put("THAT", 4);
    hashMap.put("SCREEN", 16384);
    hashMap.put("KBD", 24576);

    for (int i = 0; i < 16; i++) {
      String str = "R";
      hashMap.put(str + String.valueOf(i), i);
    }

  }

  public void addEntry(String symbol, int address) {
    hashMap.putIfAbsent(symbol, address);
  }

  public boolean contains(String symbol) {
    return hashMap.containsKey(symbol);
  }

  public int getAdress(String symbol) {
    return hashMap.get(symbol);
  }
}
