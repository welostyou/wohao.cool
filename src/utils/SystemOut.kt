package utils

import org.fusesource.jansi.Ansi
import org.fusesource.jansi.Ansi.ansi

fun Any.print() = print(this)

fun Any.println() = println(this)

fun printlnInfo(any: Any) = System.out.println(ansi().eraseScreen().fg(Ansi.Color.BLUE).a(any))

fun printlnErr(any: Any) = System.out.println(ansi().eraseScreen().fg(Ansi.Color.RED).a(any))

fun printlnGitHubInfo(any: Any) = printlnInfo("Info - GitHub - $any")

fun printlnGitHubErr(any: Any) = printlnErr("Warn - GitHub - $any")