package mofa.wangzhe.reactive.handle;

import lombok.extern.slf4j.Slf4j;
import mofa.wangzhe.reactive.model.MenuModel;
import mofa.wangzhe.reactive.service.MenuService;
import mofa.wangzhe.reactive.util.result.ResultUtil2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MenuHandle {

    private final MenuService service;

    @Autowired
    public MenuHandle(MenuService service) {
        this.service = service;
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(MenuModel.class)
                .flatMap(f -> this.service.save(f)
                        .flatMap(f2 -> ResultUtil2.ok(null)))
                .switchIfEmpty(ResultUtil2.err("请填写必要参数"));
    }

    public Mono<ServerResponse> remove(ServerRequest request) {
        String uuid = request.pathVariable("uuid");
        MenuModel model = new MenuModel();
        model.setUuid(uuid);
        return this.service.remove(model)
                .flatMap(f2 -> ResultUtil2.ok(null));
    }

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
