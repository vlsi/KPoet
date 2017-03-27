package com.grosner.kpoet

import com.squareup.javapoet.TypeName
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals

class MethodExtensionsTest : Spek({
    describe("method extensions") {
        on("can create switch statements") {

            it("generates switch break") {
                val method = method(public methodNamed "handleAction",
                        String::class paramNamed "action") {
                    switch("action") {
                        case(str("bonus")) {
                            statement("this.\$L = \$S", "name", "BONUS")
                            `break`()
                        }
                        default {
                            statement("this.\$L= \$S", "name", "NO BONUS")
                            `break`()
                        }
                    }
                }

                println(method.build().toString())
                assertEquals("public void handleAction(java.lang.String action) {\n" +
                        "  switch (action) {\n" +
                        "    case \"bonus\": {\n" +
                        "      this.name = \"BONUS\";\n" +
                        "      break;\n" +
                        "    }\n" +
                        "    default: {\n" +
                        "      this.name= \"NO BONUS\";\n" +
                        "      break;\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n", method.build().toString())
            }

            it("generates switch return") {
                val method = method(public methodNamed "handleAction",
                        String::class paramNamed "action") {
                    switch("action") {
                        case(str("bonus")) {
                            `return`(str("BONUS"))
                        }
                        default {
                            `return`(str("NO BONUS"))
                        }
                    }
                }

                println(method.build().toString())
                assertEquals("public void handleAction(java.lang.String action) {\n" +
                        "  switch (action) {\n" +
                        "    case \"bonus\": {\n" +
                        "      return \"BONUS\";\n" +
                        "    }\n" +
                        "    default: {\n" +
                        "      return \"NO BONUS\";\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n", method.build().toString())
            }
        }

        on("print for loops") {
            val method = method(public methodNamed "forLoop") {
                statement("\$T j = 0", TypeName.INT)
                `for`("\$T i = 0; i < size; i++", TypeName.INT) {
                    `if`("i > 0") {
                        `continue`()
                    }.`else if`("i < 0") {
                        statement("j++")
                    }.end()
                }
            }

            println(method.build().toString())
            assertEquals("" +
                    "public void forLoop() {\n" +
                    "  int j = 0;\n" +
                    "  for (int i = 0; i < size; i++) {\n" +
                    "    if (i > 0) {\n" +
                    "      continue;\n" +
                    "    } else if (i < 0) {\n" +
                    "      j++;\n" +
                    "    }\n" +
                    "  }\n" +
                    "}\n", method.build().toString())
        }

        on("do while loops") {
            val method = method(private methodNamed "doWhile") {
                `do`("i++") {
                    `if`("i == 5") {
                        `break`()
                    }.end()
                }.`while`("i < 0")
            }
            println(method.build().toString())
            assertEquals("private void doWhile() {\n" +
                    "  do (i++) {\n" +
                    "    if (i == 5) {\n" +
                    "      break;\n" +
                    "    }\n" +
                    "  } while (i < 0);\n" +
                    "}\n", method.build().toString())
        }
    }
})