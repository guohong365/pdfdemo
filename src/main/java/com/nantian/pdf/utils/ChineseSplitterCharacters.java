package com.nantian.pdf.utils;

import com.itextpdf.io.font.otf.GlyphLine;
import com.itextpdf.layout.splitting.DefaultSplitCharacters;

public class ChineseSplitterCharacters extends DefaultSplitCharacters {
  // line of text cannot start or end with this character
  static final char ZERO_WIDTH_NO_BREAK_SPACE = '\u2060';   //       - ZERO WIDTH NO BREAK SPACE

  //不能在行首的字符 NOT_BEGIN_CHARACTERS
  static final char KATAKANA_MIDDLE_DOT = '\u30fb';   //  ・   - KATAKANA MIDDLE DOT
  static final char BLACK_SMALL_CIRCLE = '\u2022';   //  •    - BLACK SMALL CIRCLE (BULLET)
  static final char HALFWIDTH_KATAKANA_MIDDLE_DOT = '\uff65';   //  ･    - HALFWIDTH KATAKANA MIDDLE DOT
  static final char RIGHT_CORNER_BRACKET = '\u300d';   //  」   - RIGHT CORNER BRACKET
  static final char FULLWIDTH_RIGHT_PARENTHESIS = '\uff09';   //  ）   - FULLWIDTH RIGHT PARENTHESIS
  static final char EXCLAMATION_MARK = '\u0021';   //  !    - EXCLAMATION MARK
  static final char PERCENT_SIGN = '\u0025';   //  %    - PERCENT SIGN
  static final char RIGHT_PARENTHESIS = '\u0029';   //  )    - RIGHT PARENTHESIS
  static final char COMMA = '\u002c';   //  ,    - COMMA
  static final char FULL_STOP = '\u002e';   //  .    - FULL STOP
  static final char QUESTION_MARK = '\u003f';   //  ?    - QUESTION MARK
  static final char RIGHT_SQUARE_BRACKET = '\u005d';   //  ]    - RIGHT SQUARE BRACKET
  static final char RIGHT_CURLY_BRACKET = '\u007d';   //  }    - RIGHT CURLY BRACKET
  static final char HALFWIDTH_IDEOGRAPHIC_FULL_STOP = '\uff61';   //  ｡    - HALFWIDTH IDEOGRAPHIC FULL STOP

  static final char HALFWIDTH_KATAKANA_HIRAGANA_PROLONGED_SOUND_MARK = '\uff70';   //  ｰ    - HALFWIDTH KATAKANA-HIRAGANA PROLONGED SOUND MARK
  static final char HALFWIDTH_KATAKANA_VOICED_SOUND_MARK = '\uff9e';   //  ﾞ    - HALFWIDTH KATAKANA VOICED SOUND MARK
  static final char HALFWIDTH_KATAKANA_SEMI_VOICED_SOUND_MARK = '\uff9f';   //  ﾟ    - HALFWIDTH KATAKANA SEMI-VOICED SOUND MARK
  static final char IDEOGRAPHIC_COMMA = '\u3001';   //  、    - IDEOGRAPHIC COMMA
  static final char IDEOGRAPHIC_FULL_STOP = '\u3002';   //  。    - IDEOGRAPHIC FULL STOP
  static final char FULLWIDTH_COMMA = '\uff0c';   //  ，    - FULLWIDTH COMMA
  static final char FULLWIDTH_FULL_STOP = '\uff0e';   //  ．    - FULLWIDTH FULL STOP
  static final char FULLWIDTH_COLON = '\uff1a';   //  ：    - FULLWIDTH COLON
  static final char FULLWIDTH_SEMICOLON = '\uff1b';   //  ；    - FULLWIDTH SEMICOLON
  static final char FULLWIDTH_QUESTION_MARK = '\uff1f';   //  ？    - FULLWIDTH QUESTION MARK
  static final char FULLWIDTH_EXCLAMATION_MARK = '\uff01';   //  ！    - FULLWIDTH EXCLAMATION MARK
  static final char KATAKANA_HIRAGANA_VOICED_SOUND_MARK = '\u309b';   //  ゛    - KATAKANA-HIRAGANA VOICED SOUND MARK
  static final char KATAKANA_HIRAGANA_SEMI_VOICED_SOUND_MARK = '\u309c';   //  ゜    - KATAKANA-HIRAGANA SEMI-VOICED SOUND MARK
  static final char KATAKANA_ITERATION_MARK = '\u30fd';   //  ヽ    - KATAKANA ITERATION MARK

  static final char RIGHT_SINGLE_QUOTATION_MARK = '\u2019';   //  ’    - RIGHT SINGLE QUOTATION MARK
  static final char RIGHT_DOUBLE_QUOTATION_MARK = '\u201d';   //  ”    - RIGHT DOUBLE QUOTATION MARK
  static final char RIGHT_TORTOISE_SHELL_BRACKET = '\u3015';   //  〕    - RIGHT TORTOISE SHELL BRACKET
  static final char FULLWIDTH_RIGHT_SQUARE_BRACKET = '\uff3d';   //  ］    - FULLWIDTH RIGHT SQUARE BRACKET
  static final char FULLWIDTH_RIGHT_CURLY_BRACKET = '\uff5d';   //  ｝    - FULLWIDTH RIGHT CURLY BRACKET
  static final char RIGHT_ANGLE_BRACKET = '\u3009';   //  〉    - RIGHT ANGLE BRACKET
  static final char RIGHT_DOUBLE_ANGLE_BRACKET = '\u300b';   //  》    - RIGHT DOUBLE ANGLE BRACKET
  static final char RIGHT_WHITE_CORNER_BRACKET = '\u300f';   //  』    - RIGHT WHITE CORNER BRACKET
  static final char RIGHT_BLACK_LENTICULAR_BRACKET = '\u3011';   //  】    - RIGHT BLACK LENTICULAR BRACKET
  static final char DEGREE_SIGN = '\u00b0';   //  °    - DEGREE SIGN
  static final char PRIME = '\u2032';   //  ′    - PRIME
  static final char DOUBLE_PRIME = '\u2033';   //  ″    - DOUBLE PRIME

  //不能在行末的字符 NOT_ENDING_CHARACTERS[]
  static final char DOLLAR_SIGN = '\u0024';   //  $   - DOLLAR SIGN
  static final char LEFT_PARENTHESIS = '\u0028';   //  (   - LEFT PARENTHESIS
  static final char LEFT_SQUARE_BRACKET = '\u005b';   //  [   - LEFT SQUARE BRACKET
  static final char LEFT_CURLY_BRACKET = '\u007b';   //  {   - LEFT CURLY BRACKET
  static final char POUND_SIGN = '\u00a3';   //  £   - POUND SIGN
  static final char YEN_SIGN = '\u00a5';   //  ¥   - YEN SIGN
  static final char LEFT_DOUBLE_QUOTATION_MARK = '\u201c';   //  “   - LEFT DOUBLE QUOTATION MARK
  static final char LEFT_SINGLE_QUOTATION_MARK = '\u2018';   //   ‘  - LEFT SINGLE QUOTATION MARK
  static final char LEFT_DOUBLE_ANGLE_BRACKET = '\u300a';   //  《  - LEFT DOUBLE ANGLE BRACKET
  static final char LEFT_ANGLE_BRACKET = '\u3008';   //  〈  - LEFT ANGLE BRACKET
  static final char LEFT_CORNER_BRACKET = '\u300c';   //  「  - LEFT CORNER BRACKET
  static final char LEFT_WHITE_CORNER_BRACKET = '\u300e';   //  『  - LEFT WHITE CORNER BRACKET
  static final char LEFT_BLACK_LENTICULAR_BRACKET = '\u3010';   //  【  - LEFT BLACK LENTICULAR BRACKET
  static final char LEFT_TORTOISE_SHELL_BRACKET = '\u3014';   //  〔  - LEFT TORTOISE SHELL BRACKET
  static final char HALFWIDTH_LEFT_CORNER_BRACKET = '\uff62';   //  ｢   - HALFWIDTH LEFT CORNER BRACKET
  static final char FULLWIDTH_LEFT_PARENTHESIS = '\uff08';   //  （  - FULLWIDTH LEFT PARENTHESIS
  static final char FULLWIDTH_LEFT_SQUARE_BRACKET = '\uff3b';   //  ［  - FULLWIDTH LEFT SQUARE BRACKET
  static final char FULLWIDTH_LEFT_CURLY_BRACKET = '\uff5b';   //  ｛  - FULLWIDTH LEFT CURLY BRACKET
  static final char FULLWIDTH_YEN_SIGN = '\uffe5';   //  ￥  - FULLWIDTH YEN SIGN
  static final char FULLWIDTH_DOLLAR_SIGN = '\uff04';   //  ＄  - FULLWIDTH DOLLAR SIGN

  static final char[] NOT_BEGIN_CHARACTERS = new char[]{
          KATAKANA_MIDDLE_DOT, BLACK_SMALL_CIRCLE, HALFWIDTH_KATAKANA_MIDDLE_DOT, RIGHT_CORNER_BRACKET, FULLWIDTH_RIGHT_PARENTHESIS,
          EXCLAMATION_MARK, PERCENT_SIGN, RIGHT_PARENTHESIS, COMMA, FULL_STOP, QUESTION_MARK, RIGHT_SQUARE_BRACKET, RIGHT_CURLY_BRACKET,
          HALFWIDTH_IDEOGRAPHIC_FULL_STOP, HALFWIDTH_KATAKANA_HIRAGANA_PROLONGED_SOUND_MARK, HALFWIDTH_KATAKANA_VOICED_SOUND_MARK,
          HALFWIDTH_KATAKANA_SEMI_VOICED_SOUND_MARK, IDEOGRAPHIC_COMMA, IDEOGRAPHIC_FULL_STOP, FULLWIDTH_COMMA, FULLWIDTH_FULL_STOP,
          FULLWIDTH_COLON, FULLWIDTH_SEMICOLON, FULLWIDTH_QUESTION_MARK, FULLWIDTH_EXCLAMATION_MARK, KATAKANA_HIRAGANA_VOICED_SOUND_MARK,
          KATAKANA_HIRAGANA_SEMI_VOICED_SOUND_MARK, KATAKANA_ITERATION_MARK, RIGHT_SINGLE_QUOTATION_MARK, RIGHT_DOUBLE_QUOTATION_MARK,
          RIGHT_TORTOISE_SHELL_BRACKET, FULLWIDTH_RIGHT_SQUARE_BRACKET, FULLWIDTH_RIGHT_CURLY_BRACKET, RIGHT_ANGLE_BRACKET,
          RIGHT_DOUBLE_ANGLE_BRACKET, RIGHT_WHITE_CORNER_BRACKET, RIGHT_BLACK_LENTICULAR_BRACKET, DEGREE_SIGN, PRIME, DOUBLE_PRIME,
          ZERO_WIDTH_NO_BREAK_SPACE
  };


  static final char[] NOT_ENDING_CHARACTERS = new char[]{
          DOLLAR_SIGN, LEFT_PARENTHESIS, LEFT_SQUARE_BRACKET, LEFT_CURLY_BRACKET, POUND_SIGN, YEN_SIGN, LEFT_DOUBLE_QUOTATION_MARK,
          LEFT_SINGLE_QUOTATION_MARK, LEFT_ANGLE_BRACKET, LEFT_DOUBLE_ANGLE_BRACKET, LEFT_CORNER_BRACKET, LEFT_WHITE_CORNER_BRACKET,
          LEFT_BLACK_LENTICULAR_BRACKET, LEFT_TORTOISE_SHELL_BRACKET, HALFWIDTH_LEFT_CORNER_BRACKET, FULLWIDTH_LEFT_PARENTHESIS,
          FULLWIDTH_LEFT_SQUARE_BRACKET, FULLWIDTH_LEFT_CURLY_BRACKET, FULLWIDTH_YEN_SIGN, FULLWIDTH_DOLLAR_SIGN, ZERO_WIDTH_NO_BREAK_SPACE
  };

  @Override
  public boolean isSplitCharacter(GlyphLine text, int glyphPos) {
    if (!text.get(glyphPos).hasValidUnicode()) return false;

    if (glyphPos < text.end -1) {
      int next = text.get(glyphPos + 1).getUnicode();
      for (char notBeginning : NOT_BEGIN_CHARACTERS) {
        if (next == notBeginning) {
          return false;
        }
      }
    }
    int current = text.get(glyphPos).getUnicode();
    for (char notEnding : NOT_ENDING_CHARACTERS) {
      if (current == notEnding) {
        return false;
      }
    }
    boolean isBasicLatin = Character.UnicodeBlock.of(current) == Character.UnicodeBlock.BASIC_LATIN;
    if (isBasicLatin)
      return super.isSplitCharacter(text, glyphPos);
    return true;
  }
}
