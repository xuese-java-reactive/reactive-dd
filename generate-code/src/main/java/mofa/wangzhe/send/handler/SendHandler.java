package mofa.wangzhe.send.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendHandler {

//    private final static String APPID = "wx220bd8311e563f25";
//
//    //    a2db7f2a21077453b82c598e59076d65
//    private final static String APPSECRET = "a2db7f2a21077453b82c598e59076d65";
//
//    //    获取token
//    private final static String TOKENPATH = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
//            APPID + "&secret=" + APPSECRET;
//
//    //    获取所有用户
//    private final static String USERPATH = "https://api.weixin.qq.com/cgi-bin/user/get";
//
//    //    模板ID
//    private final static String MODELID = "dQSJkkn6ezgKvwSam9cg586jh3icNK4QpJg2o3gSh_o";
//
//    //    发送消息地址
//    private final static String MESGPATH = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
//
//    /**
//     * 过滤数据
//     *
//     * @param list
//     */
//    public void filterData(List<String> list) {
//        log.info("获取设置的关键字");
////        过滤后装载
//        Map<String, String> map = new HashMap<>(0);
//
//        if (DatasUtil.list != null && DatasUtil.list.size() > 0) {
//            log.info("系统判定关键字数量不为0");
//            list.forEach(k -> {
//                int m = k.indexOf("'c':");
//                int n = k.indexOf(", 'd':");
//                String substring = "";
//
//                try {
//                    substring = k.substring(m + 4, n);
//                } catch (StringIndexOutOfBoundsException e) {
//                    log.info("抓取结果解析出现非正常情况，无正常数据:{}",e.getMessage());
//                    log.info(e.toString());
//                    return;
//                }
//
//                if (!"".equals(substring)) {
//                    for (int i = DatasUtil.list.size() - 1; i >= 0; i--) {
//                        log.info("开始判断是否包含设定的关键字");
//                        if (substring.contains(DatasUtil.list.get(i))) {
////                        解析字符串
//                            decStr(k, map);
//                            break;
//                        }
//                    }
//                }
//            });
//
//            if (map.size() > 0) {
//                log.info("过滤出包含关键字的内容");
//                String substring = map.get("value").substring(0, map.get("value").length() - 2);
//                map.put("value", substring.replaceAll("null", "").replaceAll("'", ""));
////                发送消息
//                sendFun(map);
//            } else {
//                log.info("未过滤出包含关键字的数据，终止运行后面的业务");
//            }
//        } else {
//            log.info("未设置关键字，终止运行后面的业务");
//        }
//    }
//
//    /**
//     * 解析字符串返回对象
//     *
//     * @param s
//     * @return
//     */
//    private Map<String, String> decStr(String s, Map<String, String> map) {
//        log.info("包含指定关键字，加入返回对象中");
////        int m = s.indexOf("'a':");
////        int n = s.indexOf(", 'b':");
////        String substring = s.substring(m + 4, n);
////        zhaoBiao.setA(substring);
////
////        int m2 = s.indexOf("'b':");
////        int n2 = s.indexOf(", 'c':");
////        String substring2 = s.substring(m2 + 4, n2);
////        zhaoBiao.setB(substring2);
//
//        int m3 = s.indexOf("'c':");
//        int n3 = s.indexOf(", 'd':");
//        String substring3 = s.substring(m3 + 4, n3);
//        substring3 = substring3.trim();
//
//        int m4 = s.indexOf("'d':");
//        int n4 = s.indexOf(", 'e':");
//        String substring4 = s.substring(m4 + 4, n4);
//        substring4 = substring4.trim();
//
//        if (map.containsKey("value")) {
//            map.put("value", map.get("value") + substring3 + "(" + substring4 + "),");
//        } else {
//            map.put("value", substring3 + "(" + substring4 + "),");
//        }
//
////        int m5 = s.indexOf("'e':");
////        int n5 = s.lastIndexOf("',");
////        String substring5 = s.substring(m5 + 4, n5);
////        zhaoBiao.setE(substring5);
//
//        return map;
//    }
//
//    /**
//     * 读取文件并删除文件
//     *
//     * @param fileName
//     * @return
//     */
////    public String readFile(String fileName) {
////        StringJoiner datas = new StringJoiner("");
////        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
////            stream.forEach(datas::add);//输出重定向
////        } catch (IOException e) {
////            //Your exception handling here
////            e.printStackTrace();
////        }
//////        读取完成后删除文件
////        Path path = Paths.get(fileName);
////        try {
////            Files.delete(path);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return datas.toString();
////    }
//
//
//    /**
//     * 发送消息
//     *
//     * @return
//     */
////    public Mono<ServerResponse> send(ServerRequest request) {
//////        普通java版
//////        try {
//////            String get = HttpUtil.doPost("GET", TOKENPATH, "multipart/form-data", "UTF-8", 3000, 3000);
//////            System.out.println(get);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////        }
////        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body("111111111", String.class);
////    }
//
//    /**
//     * 发送消息
//     */
//    private void sendFun(Map<String, String> map) {
//        log.info("开始发送消息");
////        获取token
//        StringJoiner sj = new StringJoiner("");
//        openId()
//                .subscribe(e -> {
//                    if (e.getErrcode() != null) {
//                        log.info("accessToken 未能成功获取");
//                        log.info(e.getErrcode() + "");
//                        log.info(e.getErrmsg());
//                    } else {
////            获取关注的人
//                        findUser(e.getAccess_token())
//                                .subscribe(u -> {
//                                    if (u.getErrcode() != null) {
//                                        log.info("获取关注人失败");
//                                        log.info(u.getErrcode() + "");
//                                        log.info(u.getErrmsg());
//                                    } else {
//                                        log.info("已获取到openId");
////                                给每个人发送消息
//                                        String[] openid = u.getData().getOpenid();
//                                        log.info("准备向所有关注此公众号的人发送消息");
//                                        for (int i = 0; i < openid.length; i++) {
//                                            sendMsg(openid[i], e.getAccess_token(), map)
//                                                    .flatMap(h -> {
//                                                        sj.add(h);
//                                                        return Mono.just(h);
//                                                    })
//                                                    .subscribe(k -> {
//                                                        log.info("已向openid：{} 发送消息", k);
//                                                    });
//                                        }
//                                    }
//                                });
//                    }
//                });
//    }
//
//    /**
//     * 根据模板和用户发送消息
//     *
//     * @param open_id
//     * @param access_token
//     * @return
//     */
//    private Mono<String> sendMsg(String open_id, String access_token, Map<String, String> map) {
//        log.info("准备向指定的openId发送消息：{}", open_id);
//        ReqMsgUtil reqMsgUtil = new ReqMsgUtil();
//        reqMsgUtil.setTouser(open_id);
//        reqMsgUtil.setTemplate_id(MODELID);
//        reqMsgUtil.setUrl(null);
//        reqMsgUtil.setTopcolor("#FF0000");
////        模板消息key --装载对应模板
//        Map<String, String> firstMap = new HashMap<>();
//        firstMap.put("value", "新的提醒");
//        reqMsgUtil.getData().put("first", firstMap);
////        时间
//        Map<String, String> keyword1Map = new HashMap<>();
//        keyword1Map.put("value", LocalDate.now().toString());
//        reqMsgUtil.getData().put("keyword1", keyword1Map);
////        商品名称
//        Map<String, String> keyword2Map = new HashMap<>();
//        keyword2Map.put("value", "有新招标项目");
//        reqMsgUtil.getData().put("keyword2", keyword2Map);
////        订单号
//        Map<String, String> keyword3Map = new HashMap<>();
//        keyword3Map.put("value", LocalTime.now().toString());
//        reqMsgUtil.getData().put("keyword3", keyword3Map);
////        具体消息
//        reqMsgUtil.getData().put("remark", map);
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = null;
//        try {
//            json = mapper.writeValueAsString(reqMsgUtil);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        System.out.println(json);
//
////        https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
//        return webClient()
//                .post()
//                .uri(MESGPATH + access_token)
//                .accept(MediaType.APPLICATION_JSON)
//                .body(Mono.just(reqMsgUtil), ReqMsgUtil.class)
//                .retrieve()
//                .bodyToMono(String.class)
//                .timeout(Duration.ofMillis(5000));
//    }
//
//
//    /**
//     * 获取所有用户
//     *
//     * @return
//     */
//    private Mono<RespUserUtil> findUser(String access_token) {
//        log.info("获取所有关注此公众号的人");
////        https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
//        return webClient()
//                .get()
//                .uri(USERPATH + "?access_token=" + access_token)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(RespUserUtil.class)
//                .timeout(Duration.ofMillis(5000));
//    }
//
//    /**
//     * 获取openid
//     *
//     * @return
//     */
//    private Mono<RespUtil> openId() {
//        log.info("开始获取openId");
//        return webClient()
//                .get()
//                .uri(TOKENPATH)
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(RespUtil.class)
////                超时设定
//                .timeout(Duration.ofMillis(5000));
//    }

}
