/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agrologic.app.network;

import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Administrator
 */
public class TestCalcCheckSum {

    String shortString = "4096 116 ";
    String graphString = "247 250 251 253 257 260 253 244 238 234 230 226 220 217 212 209 204 203 199 "
            + "198 196 199 212 226 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 "
            + "0 0 0 0 0 0 ";
    String string = "800 1 801 7 802 14 803 21 804 28 805 35 806 42 807 49 808 56 809 63 810 70 811 242 812 248"
            + " 813 238 4910 25 4911 20 4912 20 4913 20 4914 10 4915 0 4916 0 4917 0 4918 0 4919 0 4920 0 4921 0 "
            + "4922 0 4936 290 4937 270 4938 250 4939 230 4940 220 4941 220 4942 220 4943 220 4944 220 4945 220 "
            + "4946 0 4947 7 4948 4 853 0 854 14 855 21 856 28 857 35 858 42 859 49 860 56 861 63 862 70 863 336 "
            + "864 408 865 365 13154 100 13155 250 13156 500 13157 500 13158 0 13159 0 13160 0 13161 0 13162 0 13163 0 "
            + "13164 0 13165 0 13166 0 13167 150 13168 400 13169 900 13170 1400 13171 1400 13172 1400 13173 1400 "
            + "13174 1400 13175 1400 13176 1400 13177 0 13178 0 13179 0 894 0 1600 0 1601 0 1602 0 1603 0 1604 0 "
            + "1605 0 1606 -8739 1607 0 1608 -1029 22089 32383 22090 -32128 22091 -32640 22092 0 22093 -32640 22094 "
            + "-30719 22095 -31744 22096 -32253 22097 -32500 22098 -32513 22099 -32736 22100 -32704 22101 -32704 22102"
            + " -32636 22103 -17280 22104 -32640 22105 -32577 22106 224 22107 -32513 22108 -32642 22109 0 22110 0 1700 "
            + "-32738 1701 -32761 1702 -32761 1703 -32754 1704 -32753 1705 -32705 1706 -32642 1707 -32641 1708 0"
            + " 1709 -2 1710 32 1711 515 1713 7 1714 11 1715 45 1716 0 1717 14 14088 0 14089 28 14090 35 1803 0 "
            + "1804 49 1805 56 -30950 63 -30949 70 1830 59 1831 15935 1832 67 1833 30 1834 20 1835 20 1836 20 1837 10 "
            + "1838 0 1839 0 22320 0 22321 0 22322 0 22323 -14923 22324 1 30517 145 30518 290 30519 270 30520 250 "
            + "30521 230 22330 220 -30917 220 -30916 220 -30915 220 -30914 220 -30913 220 -30912 16895 -30911 0 "
            + "-30910 -515 10051 15 10052 80 10053 320 10054 321 10055 35 14152 20 14153 46 14154 94 14155 94 14156 94 "
            + "10061 97 10062 105 10063 105 10064 105 10065 105 14162 0 14163 0 14164 0 14165 0 10070 16 10071 81 "
            + "10072 321 10073 322 1897 7 -30870 2 -30869 5051 2200 170 2201 400 2202 750 2203 1250 2204 1250 2205 "
            + "1250 2206 1250 2207 1250 2208 1250 2209 1250 2210 4188 2211 16931 2212 650 2213 -7981 2214 20060 2215 "
            + "-28510 2216 -28592 2217 32419 2218 16896 2219 11224 2220 -16096 2221 -544 2222 -8081 2223 -31839 2224 "
            + "-23584 2225 22991 2226 26096 2227 -32125 2228 -23584 2229 -11518 2230 -28950 2231 -12432 2232 5344 "
            + "2233 -7947 -22342 0 -22341 -23809 -22340 23830 2237 249 2238 87 3000 44 3001 3 7098 323 7099 302 "
            + "7100 236 7101 126 3006 0 3007 0 3008 0 23489 6144 3010 84 7107 315 15300 50 3013 1 3015 0 3016 2 "
            + "3017 0 3018 0 3019 0 3020 20 7117 21845 11214 0 15311 21845 3024 287 7121 322 11218 -21846 15315 2 "
            + "3028 5030 3029 0 3030 0 3031 0 3032 0 3033 0 3034 0 3035 0 3036 0 3037 0 3038 0 3039 0 3040 0 "
            + "3041 0 3042 0 3043 0 3044 0 -29723 0 -29722 0 7146 -1 7147 -1 7148 -1 7149 -1 3054 -1 3055 -1 "
            + "3056 2 23537 0 3058 2098 3059 5986 23540 53 23541 0 23542 65 23543 0 23544 127 23545 233 3066 0 "
            + "3067 0 15356 0 3069 0 3070 0 15359 0 3072 0 3073 0 3074 0 3075 0 3076 0 3077 0 3078 0 3079 0"
            + " 3080 0 3081 0 3082 0 3083 0 3084 0 3085 0 3086 8 3087 303 3088 229 15377 2961 15378 214 15379 1"
            + " 3092 263 3093 -21846 3094 -21846 3095 86 3096 7566 3097 0 -1 0 ";

    public TestCalcCheckSum() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    private int calcChecksum(final byte[] buffer, int sot, int space) {
        int checksum = 0;

        for (int i = sot; i < space; i++) {
            checksum += buffer[i];
        }

        while ((checksum - 256) >= 0) {
            checksum -= 256;
        }

        return checksum;
    }

    protected static int overFlowErrorChecksum(final byte[] buffer, int sot, int space) {
        int checksum = 0;

        for (int i = sot; i <= space; i++) {
            if (((i + 1) <= buffer.length) && (buffer[i] == '-') && (buffer[i + 1] == '1')) {
                checksum += '6';
                checksum += '5';
                checksum += '5';
                checksum += '3';
                checksum += '5';
                i++;
            } else {
                checksum += buffer[i];
            }
        }

        while ((checksum - 256) >= 0) {
            checksum -= 256;
        }

        return checksum;
    }

    @Test
    public void testCalc() {
        int checksum = calcChecksum(shortString.getBytes(), 0, shortString.length());
        assertEquals(171, checksum);
        checksum = calcChecksum(string.getBytes(), 0, string.length());
        assertEquals(98, checksum);
        checksum = calcChecksum(graphString.getBytes(), 0, graphString.length());
        assertEquals(98, checksum);
    }
}
