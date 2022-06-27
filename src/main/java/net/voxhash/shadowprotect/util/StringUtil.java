package net.voxhash.shadowprotect.util;

import java.util.HashMap;

public class StringUtil implements Constants {

    public static String repeat(String string, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(string);
        }
        return builder.toString();
    }

    // public static double entropy(String str) {
    //     double entropy = 0;
    //     int[] freq = new int[256];
    //     for (int i = 0; i < str.length(); i++) {
    //         freq[str.charAt(i)]++;
    //     }
    //     for (int i = 0; i < freq.length; i++) {
    //         if (freq[i] > 0) {
    //             entropy += freq[i] * (Math.log(freq[i]) / Math.log(2));
    //         }
    //     }
    //     return entropy;
    // }

    public static Double capitalFrequency(String str) {
        double capitalFrequency = 0;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isWhitespace(str.charAt(i)) ||
                    !Character.isLetter(str.charAt(i)))
                continue;
            if (Character.isUpperCase(str.charAt(i))) {
                capitalFrequency++;
            }
        }
        return capitalFrequency / str.length();
    }
    

    public static Double similarity(String str1, String str2) {
        double max = Math.max(str1.length(), str2.length());
        double freq = 0;
        for (int i = 0; i < max; i++) {
            if (i >= str1.length() || i >= str2.length()) continue;
            if (str1.charAt(i) == str2.charAt(i)) {
                freq++;
            }
        }
        return freq / max;
    }

    // https://stackoverflow.com/a/38947571
    /**
     * @param str    a String
     * @param prefix a prefix
     * @return true if {@code start} starts with {@code prefix}, disregarding case
     *         sensitivity
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static boolean endsWithIgnoreCase(String str, String suffix) {
        int suffixLength = suffix.length();
        return str.regionMatches(
                true,
                str.length() - suffixLength,
                suffix,
                0,
                suffixLength);
    }

    public static int levenshteinDistance(String str1, String str2) {
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            distance[i][0] = i;
        }
        for (int j = 1; j <= str2.length(); j++) {
            distance[0][j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                distance[i][j] = Math.min(
                        distance[i - 1][j] + 1,
                        Math.min(
                                distance[i][j - 1] + 1,
                                distance[i - 1][j - 1]
                                        + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1)));
            }
        }

        return distance[str1.length()][str2.length()];
    }

    /**
     * Prevent construction.
     */
    private StringUtil() {
    }

    public static double entropy(String str) {
        double score = 0;
        int highest_vowels = 0;
        int highest_consonants = 0;
        int highest_digits = 0;
        int highest_symbols = 0;
        int vowels = 0;
        int consonants = 0;
        int digits = 0;
        int symbols = 0;
        for (int i = 0; i < str.length(); i++) {
            // Check digits
            if (Character.isDigit(str.charAt(i))) {
                digits += 1;
            } else {
                if (digits > highest_digits) {
                    highest_digits = digits;
                }
                digits = 0;
            }
            // Check vowels
            if (str.charAt(i) == 'a' ||
                    str.charAt(i) == 'e' ||
                    str.charAt(i) == 'i' ||
                    str.charAt(i) == 'o' ||
                    str.charAt(i) == 'u') {
                vowels += 1;
            } else {
                if (vowels > highest_vowels) {
                    highest_vowels = vowels;
                }
                vowels = 0;
            }
            // Check consonants
            if (str.charAt(i) == 'b' ||
                    str.charAt(i) == 'c' ||
                    str.charAt(i) == 'd' ||
                    str.charAt(i) == 'f' ||
                    str.charAt(i) == 'g' ||
                    str.charAt(i) == 'h' ||
                    str.charAt(i) == 'j' ||
                    str.charAt(i) == 'k' ||
                    str.charAt(i) == 'l' ||
                    str.charAt(i) == 'm' ||
                    str.charAt(i) == 'n' ||
                    str.charAt(i) == 'p' ||
                    str.charAt(i) == 'q' ||
                    str.charAt(i) == 'r' ||
                    str.charAt(i) == 's' ||
                    str.charAt(i) == 't' ||
                    str.charAt(i) == 'v' ||
                    str.charAt(i) == 'w' ||
                    str.charAt(i) == 'x' ||
                    str.charAt(i) == 'z') {
                consonants += 1;
            } else {
                if (consonants > highest_consonants) {
                    highest_consonants = consonants;
                }
                consonants = 0;
            }

            // Check symbols
            if (!Character.isLetter(str.charAt(i)) && !Character.isDigit(str.charAt(i))) {
                symbols += 1;
            } else {
                if (symbols > highest_symbols) {
                    highest_symbols = symbols;
                }
                symbols = 0;
            }

            if (highest_digits > digits) {
                digits = highest_digits;
            }

            if (highest_consonants > consonants) {
                consonants = highest_consonants;
            }

            if (highest_vowels > vowels) {
                vowels = highest_vowels;
            }

            if (highest_symbols > symbols) {
                symbols = highest_symbols;
            }

            if (digits >= 5) {
                score += 5 + (digits - 5);
            }

            if (vowels >= 4) {
                score += 4 + (vowels - 4);
            }

            if (consonants >= 4) {
                score += 4 + (consonants - 4);
            }

            if (symbols >= 5) {
                score += 5 + (symbols - 5);
            }

            for (String key : triples_text.keySet()) {
                if (str.length() > 2) {
                    if (key.charAt(0) == str.charAt(0) && key.charAt(1) == str.charAt(1)) {
                        if (str.equals(triples_text.get(key))) {
                            score += 1;
                        }
                    }
                }
            }
        }
        return score;
    }

    private static HashMap<String, String> triples_text = new HashMap<String, String>() {
        {
            put("aj", "fqtvxz");
            put("aq", "deghjkmnprtxyz");
            put("av", "bfhjqwxz");
            put("az", "jwx");
            put("bd", "bghjkmpqvxz");
            put("bf", "bcfgjknpqvwxyz");
            put("bg", "bdfghjkmnqstvxz");
            put("bh", "bfhjkmnqvwxz");
            put("bj", "bcdfghjklmpqtvwxyz");
            put("bk", "dfjkmqrvwxyz");
            put("bl", "bgpqwxz");
            put("bm", "bcdflmnqz");
            put("bn", "bghjlmnpqtvwx");
            put("bp", "bfgjknqvxz");
            put("bq", "bcdefghijklmnopqrstvwxyz");
            put("bt", "dgjkpqtxz");
            put("bv", "bfghjklnpqsuvwxz");
            put("bw", "bdfjknpqsuwxyz");
            put("bx", "abcdfghijklmnopqtuvwxyz");
            put("bz", "bcdfgjklmnpqrstvwxz");
            put("cb", "bfghjkpqyz");
            put("cc", "gjqxz");
            put("cd", "hjkqvwxz");
            put("cf", "gjknqvwyz");
            put("cg", "bdfgjkpqvz");
            put("cl", "fghjmpqxz");
            put("cm", "bjkqv");
            put("cn", "bghjkpqwxz");
            put("cp", "gjkvxyz");
            put("cq", "abcdefghijklmnopqsvwxyz");
            put("cr", "gjqx");
            put("cs", "gjxz");
            put("cv", "bdfghjklmnquvwxyz");
            put("cx", "abdefghjklmnpqrstuvwxyz");
            put("cy", "jqy");
            put("cz", "bcdfghjlpqrtvwxz");
            put("db", "bdgjnpqtxz");
            put("dc", "gjqxz");
            put("dd", "gqz");
            put("df", "bghjknpqvxyz");
            put("dg", "bfgjqvxz");
            put("dh", "bfkmnqwxz");
            put("dj", "bdfghjklnpqrwxz");
            put("dk", "cdhjkpqrtuvwxz");
            put("dl", "bfhjknqwxz");
            put("dm", "bfjnqw");
            put("dn", "fgjkmnpqvwz");
            put("dp", "bgjkqvxz");
            put("dq", "abcefghijkmnopqtvwxyz");
            put("dr", "bfkqtvx");
            put("dt", "qtxz");
            put("dv", "bfghjknqruvwyz");
            put("dw", "cdfjkmnpqsvwxz");
            put("dx", "abcdeghjklmnopqrsuvwxyz");
            put("dy", "jyz");
            put("dz", "bcdfgjlnpqrstvxz");
            put("eb", "jqx");
            put("eg", "cjvxz");
            put("eh", "hxz");
            put("ej", "fghjpqtwxyz");
            put("ek", "jqxz");
            put("ep", "jvx");
            put("eq", "bcghijkmotvxyz");
            put("ev", "bfpq");
            put("fc", "bdjkmnqvxz");
            put("fd", "bgjklqsvyz");
            put("fg", "fgjkmpqtvwxyz");
            put("fh", "bcfghjkpqvwxz");
            put("fj", "bcdfghijklmnpqrstvwxyz");
            put("fk", "bcdfghjkmpqrstvwxz");
            put("fl", "fjkpqxz");
            put("fm", "dfhjlmvwxyz");
            put("fn", "bdfghjklnqrstvwxz");
            put("fp", "bfjknqtvwxz");
            put("fq", "abcefghijklmnopqrstvwxyz");
            put("fr", "nqxz");
            put("fs", "gjxz");
            put("ft", "jqx");
            put("fv", "bcdfhjklmnpqtuvwxyz");
            put("fw", "bcfgjklmpqstuvwxyz");
            put("fx", "bcdfghjklmnpqrstvwxyz");
            put("fy", "ghjpquvxy");
            put("fz", "abcdfghjklmnpqrtuvwxyz");
            put("gb", "bcdknpqvwx");
            put("gc", "gjknpqwxz");
            put("gd", "cdfghjklmqtvwxz");
            put("gf", "bfghjkmnpqsvwxyz");
            put("gg", "jkqvxz");
            put("gj", "bcdfghjklmnpqrstvwxyz");
            put("gk", "bcdfgjkmpqtvwxyz");
            put("gl", "fgjklnpqwxz");
            put("gm", "dfjkmnqvxz");
            put("gn", "jkqvxz");
            put("gp", "bjknpqtwxyz");
            put("gq", "abcdefghjklmnopqrsvwxyz");
            put("gr", "jkqt");
            put("gt", "fjknqvx");
            put("gu", "qwx");
            put("gv", "bcdfghjklmpqstvwxyz");
            put("gw", "bcdfgjknpqtvwxz");
            put("gx", "abcdefghjklmnopqrstvwxyz");
            put("gy", "jkqxy");
            put("gz", "bcdfgjklmnopqrstvxyz");
            put("hb", "bcdfghjkqstvwxz");
            put("hc", "cjknqvwxz");
            put("hd", "fgjnpvz");
            put("hf", "bfghjkmnpqtvwxyz");
            put("hg", "bcdfgjknpqsxyz");
            put("hh", "bcgklmpqrtvwxz");
            put("hj", "bcdfgjkmpqtvwxyz");
            put("hk", "bcdgkmpqrstvwxz");
            put("hl", "jxz");
            put("hm", "dhjqrvwxz");
            put("hn", "jrxz");
            put("hp", "bjkmqvwxyz");
            put("hq", "abcdefghijklmnopqrstvwyz");
            put("hr", "cjqx");
            put("hs", "jqxz");
            put("hv", "bcdfgjklmnpqstuvwxz");
            put("hw", "bcfgjklnpqsvwxz");
            put("hx", "abcdefghijklmnopqrstuvwxyz");
            put("hz", "bcdfghjklmnpqrstuvwxz");
            put("ib", "jqx");
            put("if", "jqvwz");
            put("ih", "bgjqx");
            put("ii", "bjqxy");
            put("ij", "cfgqxy");
            put("ik", "bcfqx");
            put("iq", "cdefgjkmnopqtvxyz");
            put("iu", "hiwxy");
            put("iv", "cfgmqx");
            put("iw", "dgjkmnpqtvxz");
            put("ix", "jkqrxz");
            put("iy", "bcdfghjklpqtvwx");
            put("jb", "bcdghjklmnopqrtuvwxyz");
            put("jc", "cfgjkmnopqvwxy");
            put("jd", "cdfghjlmnpqrtvwx");
            put("jf", "abcdfghjlnopqrtuvwxyz");
            put("jg", "bcdfghijklmnopqstuvwxyz");
            put("jh", "bcdfghjklmnpqrxyz");
            put("jj", "bcdfghjklmnopqrstuvwxyz");
            put("jk", "bcdfghjknqrtwxyz");
            put("jl", "bcfghjmnpqrstuvwxyz");
            put("jm", "bcdfghiklmnqrtuvwyz");
            put("jn", "bcfjlmnpqsuvwxz");
            put("jp", "bcdfhijkmpqstvwxyz");
            put("jq", "abcdefghijklmnopqrstuvwxyz");
            put("jr", "bdfhjklpqrstuvwxyz");
            put("js", "bfgjmoqvxyz");
            put("jt", "bcdfghjlnpqrtvwxz");
            put("jv", "abcdfghijklpqrstvwxyz");
            put("jw", "bcdefghijklmpqrstuwxyz");
            put("jx", "abcdefghijklmnopqrstuvwxyz");
            put("jy", "bcdefghjkpqtuvwxyz");
            put("jz", "bcdfghijklmnopqrstuvwxyz");
            put("kb", "bcdfghjkmqvwxz");
            put("kc", "cdfgjknpqtwxz");
            put("kd", "bfghjklmnpqsvwxyz");
            put("kf", "bdfghjkmnpqsvwxyz");
            put("kg", "cghjkmnqtvwxyz");
            put("kh", "cfghjkqx");
            put("kj", "bcdfghjkmnpqrstwxyz");
            put("kk", "bcdfgjmpqswxz");
            put("kl", "cfghlmqstwxz");
            put("km", "bdfghjknqrstwxyz");
            put("kn", "bcdfhjklmnqsvwxz");
            put("kp", "bdfgjkmpqvxyz");
            put("kq", "abdefghijklmnopqrstvwxyz");
            put("kr", "bcdfghjmqrvwx");
            put("ks", "jqx");
            put("kt", "cdfjklqvx");
            put("ku", "qux");
            put("kv", "bcfghjklnpqrstvxyz");
            put("kw", "bcdfgjklmnpqsvwxz");
            put("kx", "abcdefghjklmnopqrstuvwxyz");
            put("ky", "vxy");
            put("kz", "bcdefghjklmnpqrstuvwxyz");
            put("lb", "cdgkqtvxz");
            put("lc", "bqx");
            put("lg", "cdfgpqvxz");
            put("lh", "cfghkmnpqrtvx");
            put("lk", "qxz");
            put("ln", "cfjqxz");
            put("lp", "jkqxz");
            put("lq", "bcdefhijklmopqrstvwxyz");
            put("lr", "dfgjklmpqrtvwx");
            put("lv", "bcfhjklmpwxz");
            put("lw", "bcdfgjknqxz");
            put("lx", "bcdfghjklmnpqrtuwz");
            put("lz", "cdjptvxz");
            put("mb", "qxz");
            put("md", "hjkpvz");
            put("mf", "fkpqvwxz");
            put("mg", "cfgjnpqsvwxz");
            put("mh", "bchjkmnqvx");
            put("mj", "bcdfghjknpqrstvwxyz");
            put("mk", "bcfgklmnpqrvwxz");
            put("ml", "jkqz");
            put("mm", "qvz");
            put("mn", "fhjkqxz");
            put("mq", "bdefhjklmnopqtwxyz");
            put("mr", "jklqvwz");
            put("mt", "jkq");
            put("mv", "bcfghjklmnqtvwxz");
            put("mw", "bcdfgjklnpqsuvwxyz");
            put("mx", "abcefghijklmnopqrstvwxyz");
            put("mz", "bcdfghjkmnpqrstvwxz");
            put("nb", "hkmnqxz");
            put("nf", "bghqvxz");
            put("nh", "fhjkmqtvxz");
            put("nk", "qxz");
            put("nl", "bghjknqvwxz");
            put("nm", "dfghjkqtvwxz");
            put("np", "bdjmqwxz");
            put("nq", "abcdfghjklmnopqrtvwxyz");
            put("nr", "bfjkqstvx");
            put("nv", "bcdfgjkmnqswxz");
            put("nw", "dgjpqvxz");
            put("nx", "abfghjknopuyz");
            put("nz", "cfqrxz");
            put("oc", "fjvw");
            put("og", "qxz");
            put("oh", "fqxz");
            put("oj", "bfhjmqrswxyz");
            put("ok", "qxz");
            put("oq", "bcdefghijklmnopqrstvwxyz");
            put("ov", "bfhjqwx");
            put("oy", "qxy");
            put("oz", "fjpqtvx");
            put("pb", "fghjknpqvwz");
            put("pc", "gjq");
            put("pd", "bgjkvwxz");
            put("pf", "hjkmqtvwyz");
            put("pg", "bdfghjkmqsvwxyz");
            put("ph", "kqvx");
            put("pk", "bcdfhjklmpqrvx");
            put("pl", "ghkqvwx");
            put("pm", "bfhjlmnqvwyz");
            put("pn", "fjklmnqrtvwz");
            put("pp", "gqwxz");
            put("pq", "abcdefghijklmnopqstvwxyz");
            put("pr", "hjkqrwx");
            put("pt", "jqxz");
            put("pv", "bdfghjklquvwxyz");
            put("pw", "fjkmnpqsuvwxz");
            put("px", "abcdefghijklmnopqrstuvwxyz");
            put("pz", "bdefghjklmnpqrstuvwxyz");
            put("qa", "ceghkopqxy");
            put("qb", "bcdfghjklmnqrstuvwxyz");
            put("qc", "abcdfghijklmnopqrstuvwxyz");
            put("qd", "defghijklmpqrstuvwxyz");
            put("qe", "abceghjkmopquwxyz");
            put("qf", "abdfghijklmnopqrstuvwxyz");
            put("qg", "abcdefghijklmnopqrtuvwxz");
            put("qh", "abcdefghijklmnopqrstuvwxyz");
            put("qi", "efgijkmpwx");
            put("qj", "abcdefghijklmnopqrstuvwxyz");
            put("qk", "abcdfghijklmnopqrsuvwxyz");
            put("ql", "abcefghjklmnopqrtuvwxyz");
            put("qm", "bdehijklmnoqrtuvxyz");
            put("qn", "bcdefghijklmnoqrtuvwxyz");
            put("qo", "abcdefgijkloqstuvwxyz");
            put("qp", "abcdefghijkmnopqrsuvwxyz");
            put("qq", "bcdefghijklmnopstwxyz");
            put("qr", "bdefghijklmnoqruvwxyz");
            put("qs", "bcdefgijknqruvwxz");
            put("qt", "befghjklmnpqtuvwxz");
            put("qu", "cfgjkpwz");
            put("qv", "abdefghjklmnopqrtuvwxyz");
            put("qw", "bcdfghijkmnopqrstuvwxyz");
            put("qx", "abcdefghijklmnopqrstuvwxyz");
            put("qy", "abcdefghjklmnopqrstuvwxyz");
            put("qz", "abcdefghijklmnopqrstuvwxyz");
            put("rb", "fxz");
            put("rg", "jvxz");
            put("rh", "hjkqrxz");
            put("rj", "bdfghjklmpqrstvwxz");
            put("rk", "qxz");
            put("rl", "jnq");
            put("rp", "jxz");
            put("rq", "bcdefghijklmnopqrtvwxy");
            put("rr", "jpqxz");
            put("rv", "bcdfghjmpqrvwxz");
            put("rw", "bfgjklqsvxz");
            put("rx", "bcdfgjkmnopqrtuvwxz");
            put("rz", "djpqvxz");
            put("sb", "kpqtvxz");
            put("sd", "jqxz");
            put("sf", "bghjkpqw");
            put("sg", "cgjkqvwxz");
            put("sj", "bfghjkmnpqrstvwxz");
            put("sk", "qxz");
            put("sl", "gjkqwxz");
            put("sm", "fkqwxz");
            put("sn", "dhjknqvwxz");
            put("sq", "bfghjkmopstvwxz");
            put("sr", "jklqrwxz");
            put("sv", "bfhjklmnqtwxyz");
            put("sw", "jkpqvwxz");
            put("sx", "bcdefghjklmnopqrtuvwxyz");
            put("sy", "qxy");
            put("sz", "bdfgjpqsvxz");
            put("tb", "cghjkmnpqtvwx");
            put("tc", "jnqvx");
            put("td", "bfgjkpqtvxz");
            put("tf", "ghjkqvwyz");
            put("tg", "bdfghjkmpqsx");
            put("tj", "bdfhjklmnpqstvwxyz");
            put("tk", "bcdfghjklmpqvwxz");
            put("tl", "jkqwxz");
            put("tm", "bknqtwxz");
            put("tn", "fhjkmqvwxz");
            put("tp", "bjpqvwxz");
            put("tq", "abdefhijklmnopqrstvwxyz");
            put("tr", "gjqvx");
            put("tv", "bcfghjknpquvwxz");
            put("tw", "bcdfjknqvz");
            put("tx", "bcdefghjklmnopqrsuvwxz");
            put("tz", "jqxz");
            put("uc", "fjmvx");
            put("uf", "jpqvx");
            put("ug", "qvx");
            put("uh", "bcgjkpvxz");
            put("uj", "wbfghklmqvwx");
            put("uk", "fgqxz");
            put("uq", "bcdfghijklmnopqrtwxyz");
            put("uu", "fijkqvwyz");
            put("uv", "bcdfghjkmpqtwxz");
            put("uw", "dgjnquvxyz");
            put("ux", "jqxz");
            put("uy", "jqxyz");
            put("uz", "fgkpqrx");
            put("vb", "bcdfhijklmpqrtuvxyz");
            put("vc", "bgjklnpqtvwxyz");
            put("vd", "bdghjklnqvwxyz");
            put("vf", "bfghijklmnpqtuvxz");
            put("vg", "bcdgjkmnpqtuvwxyz");
            put("vh", "bcghijklmnpqrtuvwxyz");
            put("vj", "abcdfghijklmnpqrstuvwxyz");
            put("vk", "bcdefgjklmnpqruvwxyz");
            put("vl", "hjkmpqrvwxz");
            put("vm", "bfghjknpquvxyz");
            put("vn", "bdhjkmnpqrtuvwxz");
            put("vp", "bcdeghjkmopqtuvwyz");
            put("vq", "abcdefghijklmnopqrstvwxyz");
            put("vr", "fghjknqrtvwxz");
            put("vs", "dfgjmqz");
            put("vt", "bdfgjklmnqtx");
            put("vu", "afhjquwxy");
            put("vv", "cdfghjkmnpqrtuwxz");
            put("vw", "abcdefghijklmnopqrtuvwxyz");
            put("vx", "abcefghjklmnopqrstuvxyz");
            put("vy", "oqx");
            put("vz", "abcdefgjklmpqrstvwxyz");
            put("wb", "bdfghjpqtvxz");
            put("wc", "bdfgjkmnqvwx");
            put("wd", "dfjpqvxz");
            put("wf", "cdghjkmqvwxyz");
            put("wg", "bcdfgjknpqtvwxyz");
            put("wh", "cdghjklpqvwxz");
            put("wj", "bfghijklmnpqrstvwxyz");
            put("wk", "cdfgjkpqtuvxz");
            put("wl", "jqvxz");
            put("wm", "dghjlnqtvwxz");
            put("wp", "dfgjkpqtvwxz");
            put("wq", "abcdefghijklmnopqrstvwxyz");
            put("wr", "cfghjlmpqwx");
            put("wt", "bdgjlmnpqtvx");
            put("wu", "aikoquvwy");
            put("wv", "bcdfghjklmnpqrtuvwxyz");
            put("ww", "bcdgkpqstuvxyz");
            put("wx", "abcdefghijklmnopqrstuvwxz");
            put("wy", "jquwxy");
            put("wz", "bcdfghjkmnopqrstuvwxz");
            put("xa", "ajoqy");
            put("xb", "bcdfghjkmnpqsvwxz");
            put("xc", "bcdgjkmnqsvwxz");
            put("xd", "bcdfghjklnpqstuvwxyz");
            put("xf", "bcdfghjkmnpqtvwxyz");
            put("xg", "bcdfghjkmnpqstvwxyz");
            put("xh", "cdfghjkmnpqrstvwxz");
            put("xi", "jkqy");
            put("xj", "abcdefghijklmnopqrstvwxyz");
            put("xk", "abcdfghjkmnopqrstuvwxyz");
            put("xl", "bcdfghjklmnpqrvwxz");
            put("xm", "bcdfghjknpqvwxz");
            put("xn", "bcdfghjklmnpqrvwxyz");
            put("xp", "bcfjknpqvxz");
            put("xq", "abcdefghijklmnopqrstvwxyz");
            put("xr", "bcdfghjklnpqrsvwyz");
            put("xs", "bdfgjmnqrsvxz");
            put("xt", "jkpqvwxz");
            put("xu", "fhjkquwx");
            put("xv", "bcdefghjklmnpqrsuvwxyz");
            put("xw", "bcdfghjklmnpqrtuvwxyz");
            put("xx", "bcdefghjkmnpqrstuwyz");
            put("xy", "jxy");
            put("xz", "abcdefghjklmnpqrstuvwxyz");
            put("yb", "cfghjmpqtvwxz");
            put("yc", "bdfgjmpqsvwx");
            put("yd", "chjkpqvwx");
            put("yf", "bcdghjmnpqsvwx");
            put("yg", "cfjkpqtxz");
            put("yh", "bcdfghjkpqx");
            put("yi", "hjqwxy");
            put("yj", "bcdfghjklmnpqrstvwxyz");
            put("yk", "bcdfgpqvwxz");
            put("ym", "dfgjqvxz");
            put("yp", "bcdfgjkmqxz");
            put("yq", "abcdefghijklmnopqrstvwxyz");
            put("yr", "jqx");
            put("yt", "bcfgjnpqx");
            put("yv", "bcdfghjlmnpqstvwxz");
            put("yw", "bfgjklmnpqstuvwxz");
            put("yx", "bcdfghjknpqrstuvwxz");
            put("yy", "bcdfghjklpqrstvwxz");
            put("yz", "bcdfjklmnpqtvwx");
            put("zb", "dfgjklmnpqstvwxz");
            put("zc", "bcdfgjmnpqstvwxy");
            put("zd", "bcdfghjklmnpqstvwxy");
            put("zf", "bcdfghijkmnopqrstvwxyz");
            put("zg", "bcdfgjkmnpqtvwxyz");
            put("zh", "bcfghjlpqstvwxz");
            put("zj", "abcdfghjklmnpqrstuvwxyz");
            put("zk", "bcdfghjklmpqstvwxz");
            put("zl", "bcdfghjlnpqrstvwxz");
            put("zm", "bdfghjklmpqstvwxyz");
            put("zn", "bcdfghjlmnpqrstuvwxz");
            put("zp", "bcdfhjklmnpqstvwxz");
            put("zq", "abcdefghijklmnopqrstvwxyz");
            put("zr", "bcfghjklmnpqrstvwxyz");
            put("zs", "bdfgjmnqrsuwxyz");
            put("zt", "bcdfgjkmnpqtuvwxz");
            put("zu", "ajqx");
            put("zv", "bcdfghjklmnpqrstuvwxyz");
            put("zw", "bcdfghjklmnpqrstuvwxyz");
            put("zx", "abcdefghijklmnopqrstuvwxyz");
            put("zy", "fxy");
            put("zz", "cdfhjnpqrvx");
        }
    };
}
