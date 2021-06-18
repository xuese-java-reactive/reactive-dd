package mofa.wangzhe.reactive.handle;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.AccountService;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.jwt.JwtUtil;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author LD
 */

@Slf4j
@Component
public class MenuHandler {

    private final MenuService service;
    private final AccountService accountService;

    @Autowired
    public MenuHandler(MenuService service, AccountService accountService) {
        this.service = service;
        this.accountService = accountService;
    }

    @Secured("DEVELOPMENT")
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(MenuModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    @Secured("DEVELOPMENT")
    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        MenuModel model = new MenuModel();
        model.setUuid(uuid);
        return this.service.remove(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

    @Secured("DEVELOPMENT")
    public Mono<ServerResponse> update(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        return request.bodyToMono(MenuModel.class)
                .flatMap(f -> {
                    f.setUuid(uuid);
                    return this.service.update(f)
                            .flatMap(f2 -> ResultUtil2.ok(null));
                })
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    /**
     * menu list
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> list(ServerRequest request) {
        String pid = request.pathVariable("pid");
        return this.service.findAll(pid)
                .collectList()
                .flatMap(f -> Mono.just(getTreeList(f)))
                .flatMap(ResultUtil2::ok);
    }

    public Mono<ServerResponse> list2(ServerRequest request) {
        String token = request.headers()
                .firstHeader("auth");
        if (token == null) {
            return Mono.error(new Exception("没有检测到令牌，请从新登录"));
        }

        Claim aud = JwtUtil.getClaim(token, "aud");
        assert aud != null : "令牌错误，请从新登录";
        return this.accountService.one(aud.asString())
                .flatMap(fm -> {
                    String development = "development", admins = "admins";
                    if (Objects.equals(development, fm.getOrg())) {
                        List<MenuModel> list = new ArrayList<>();
                        MenuModel model = new MenuModel();
                        model.setPid("0");
                        model.setName("开发者菜单");
                        model.setOrders(0);

                        List<MenuModel> list2 = new ArrayList<>();
                        MenuModel model1 = new MenuModel();
                        model1.setName("系统特殊管理");
                        model1.setOrders(0);
                        model1.setP("sys/sys");
                        list2.add(model1);
                        MenuModel model2 = new MenuModel();
                        model2.setName("菜单管理");
                        model2.setOrders(1);
                        model2.setP("menu/menu");
                        list2.add(model2);

                        model.setChildren(list2);
                        list.add(model);
                        return ResultUtil2.ok(list);
                    } else if (Objects.equals(admins, fm.getOrg())) {
                        List<MenuModel> list = new ArrayList<>();
                        MenuModel model = new MenuModel();
                        model.setPid("0");
                        model.setName("超级管理员菜单");
                        model.setOrders(0);

                        List<MenuModel> list2 = new ArrayList<>();
                        MenuModel model1 = new MenuModel();
                        model1.setName("权限管理");
                        model1.setOrders(0);
                        model1.setP("jur/jur");
                        list2.add(model1);
                        MenuModel model2 = new MenuModel();
                        model2.setName("组织机构管理");
                        model2.setOrders(1);
                        model2.setP("org/org");
                        list2.add(model2);

                        model.setChildren(list2);
                        list.add(model);
                        return ResultUtil2.ok(list);
                    } else {
                        return this.service.findAll2(aud.asString(), 0)
                                .collectList()
                                .flatMap(f -> Mono.just(getTreeList(f)))
                                .flatMap(ResultUtil2::ok);
                    }
                })
                .switchIfEmpty(ResultUtil2.err("logout"));
    }

    private static List<MenuModel> getTreeList(List<MenuModel> entityList) {
        List<MenuModel> resultList = new ArrayList<>();
        //获取顶层元素集合
        String pid;
        for (MenuModel entity : entityList) {
            pid = entity.getPid();
            //顶层元素的pid==null或者为0
            if (Objects.equals("0", pid)) {
                resultList.add(entity);
            }
        }
        //获取每个顶层元素的子数据集合
        for (MenuModel entity : resultList) {
            entity.setChildren(getSubList(entity.getUuid(), entityList));
        }
        return resultList;
    }

    private static List<MenuModel> getSubList(String id, List<MenuModel> entityList) {
        List<MenuModel> childList = new ArrayList<>();
        String pid;
        //子集的直接子对象
        for (MenuModel entity : entityList) {
            pid = entity.getPid();
            if (id.equals(pid)) {
                childList.add(entity);
            }
        }
        //子集的间接子对象
        for (MenuModel entity : childList) {
            entity.setChildren(getSubList(entity.getUuid(), entityList));
        }
        //递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
