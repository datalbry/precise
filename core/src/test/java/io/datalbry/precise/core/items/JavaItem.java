package io.datalbry.precise.core.items;

import io.datalbry.precise.api.schema.Nullable;

public class JavaItem {

  @Nullable private final String name;
  private final int number;

  public JavaItem(String name, int number) {
    this.name = name;
    this.number = number;
  }

  @Nullable public String getName() {
    return name;
  }

  public int getNumber() {
    return number;
  }
}
