package com.gangtise.gdk.result.dataprocess;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * websocket:-------------------------------------
 * {
 * 	"type": "queryResult",
 * 	"gid": {
 * 		"fm": "Api",
 * 		"pg": "sdk",
 * 		"idx": "1"
 *        },
 * 	"result": [{
 * 		"table": {
 * 			"field": [
 * 				["Time", "tm"],
 * 				["Open", "x2"],
 * 				["High", "x2"],
 * 				["Low", "x2"],
 * 				["Close", "x2"],
 * 				["Volume", "i2"],
 * 				["Amount", "i"]
 * 			],
 * 			"info": {
 * 				"type": "updateappend",
 * 				"rank": "col",
 * 				"compressed": "0",
 * 				"mode": "table",
 * 				"rows": 20,
 * 				"begin": 77,
 * 				"totalRows": 96,
 * 				"thisBegin": 77,
 * 				"dataRange": 20
 *            },
 * 			"gid": {
 * 				"name": "quote",
 * 				"id": 0,
 * 				"idx": 0
 *            },
 * 			"flags": {
 * 				"open": [104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104],
 * 				"high": [104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104],
 * 				"low": [104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104, 104],
 * 				"close": [106, 105, 106, 105, 105, 104, 106, 106, 105, 105, 106, 104, 105, 105, 105, 106, 105, 106, 105, 105]
 *            },
 * 			"data": [
 * 				[1606195200000, 1606195500000, 1606195800000, 1606196100000, 1606196400000, 1606196700000, 1606197000000, 1606197300000, 1606197600000, 1606197900000, 1606198200000, 1606198500000, 1606198800000, 1606199100000, 1606199400000, 1606199700000, 1606200000000, 1606200300000, 1606200600000, 1606200900000],
 * 				[1924, 1921, 1924, 1920, 1927, 1928, 1928, 1924, 1926, 1923, 1930, 1926, 1927, 1926, 1935, 1938, 1929, 1933, 1928, 1931],
 * 				[1926, 1929, 1926, 1928, 1933, 1930, 1929, 1927, 1926, 1933, 1932, 1934, 1930, 1936, 1939, 1938, 1935, 1934, 1932, 1936],
 * 				]
 * 			"adjust": [
 * 				[19910817, 1.157490],
 * 				[19920323, 1.747000],
 * 				[19930524, 3.327150],
 * 				[19940711, 5.323440]
 * 			],
 * 			"factor": {
 * 				"table": {
 * 					"field": [
 * 						["Date", "dd"],
 * 						["Factor", "x6"],
 * 						["Info", "c"]
 * 					],
 * 					"info": {
 * 						"type": "update",
 * 						"rank": "row",
 * 						"compressed": "0",
 * 						"mode": "table",
 * 						"rows": 22,
 * 						"begin": 1,
 * 						"totalRows": 22,
 * 						"thisBegin": 1,
 * 						"dataRange": 22
 *                    },
 * 					"gid": {
 * 						"name": "quote",
 * 						"id": 0,
 * 						"idx": 0
 *                    },
 * 					"data": [
 * 						[19910817, 1157490, "每10股 配股3.0股 配股价12.0元 "],
 * 						[19920323, 1747000, "每10股 送5.0股 红利2.0元 "],
 * 						[19930524, 3327150, "每10股 送3.5股 转增5.0股 红利3.0元 配股1.0股 配股价16.0元 "],
 * 						[19940711, 5323440, "每10股 送3.0股 转增2.0股 红利5.0元 配股1.0股 配股价5.0元 "]
 * 					],
 * 					"flags": {}
 *                }
 *            }
 *        }
 *    }]
 * }
 *
 *
 * http：-------------------------------------
 *{
 * 	"data": {
 * 		"result": [{
 * 			"httpId": 1,
 * 			"table": {
 * 				"data": [
 * 					["300001.SZ", "特 锐 德"],
 * 					["300002.SZ", "神州泰岳"],
 * 					["300003.SZ", "乐普医疗"],
 * 					["300004.SZ", "南风股份"],
 * 					["300005.SZ", "探 路 者"],
 * 					["300006.SZ", "莱美药业"],
 * 					["300007.SZ", "汉威科技"],
 * 					["300008.SZ", "天海防务"],
 * 					["300009.SZ", "安科生物"],
 * 					["300010.SZ", "豆神教育"],
 * 					["300011.SZ", "鼎汉技术"],
 * 					["300012.SZ", "华测检测"],
 * 					["300013.SZ", "新宁物流"],
 * 					["300014.SZ", "亿纬锂能"],
 * 					["300015.SZ", "爱尔眼科"],
 * 					["300016.SZ", "北陆药业"],
 * 					["300017.SZ", "网宿科技"],
 * 					["300018.SZ", "中元股份"],
 * 					["300019.SZ", "硅宝科技"],
 * 					["300020.SZ", "银江股份"]
 * 				],
 * 				"dataStatus": [],
 * 				"field": [
 * 					["sufsecucode", "t"],
 * 					["secuabbr", "t"]
 * 				],
 * 				"flags": {},
 * 				"info": {
 * 					"begin": 0,
 * 					"compressed": "0",
 * 					"dataBegin": 0,
 * 					"dataRange": 20,
 * 					"dataRows": 20,
 * 					"id": 1,
 * 					"mode": "table",
 * 					"msg": "success",
 * 					"name": "staic",
 * 					"rank": "row",
 * 					"rows": 20,
 * 					"thisBegin": 0,
 * 					"thisBegin ": 1,
 * 					"totalRows": 966,
 * 					"type": "update"
 *                                }* 			},
 * 			"updateCounter": 21        0
 *            }]
 * 	},
 * 	"id": "2"
 * }
 *
 */
public class DataSource {

    private String id;
    private GData data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GData getData() {
        return data;
    }

    public void setData(GData data) {
        this.data = data;
    }


}