# Structures

```bnf
<field> ::= <whitespace> <declaration> ";" <whitespace>
<fields> ::= "" | <field> | <field>
<structure> ::= "struct" <whitespace> <name> "{" <fields> "}"
```

Structures must have a name.