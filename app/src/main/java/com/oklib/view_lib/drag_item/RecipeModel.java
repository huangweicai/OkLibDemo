package com.oklib.view_lib.drag_item;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.oklib.R;
import com.oklib.util.FastJsonUtil;
import com.oklib.util.toast.ToastUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Storm
 */
public class RecipeModel implements IRecipeModel {

    private boolean hsb, cm, ec;

    private Handler handler;
    private Activity activity;

    public RecipeModel(Context context) {
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        handler = new Handler(Looper.getMainLooper());

        //TODO temp
        String processName = context.getApplicationInfo().processName;
        hsb = "com.midea.hsb".equals(processName);
        cm = "com.midea.cm".equals(processName);
        ec = "com.midea.ec".equals(processName);
    }

    private boolean isActivityRunning() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity == null || (!activity.isFinishing() && !activity.isDestroyed());
        }
        return false;
    }

    @Override
    public void loadLocalMajorCategories(SyncCallback<List<String>> callback) {
        List<String> list = hsb ? Arrays.asList("热饮", "冷饮", "研磨") :
                Arrays.asList("肉类", "水产类", "蔬菜类", "菌菇类", "豆制品");
        callback.onSuccess(list);
    }

    @Override
    public void loadLocalMinorCategories(String majorCategory, SyncCallback<List<String>> callback) {
        List<String> list = hsb ? Arrays.asList("汤类", "粥类", "糊类", "豆浆") :
                Arrays.asList("猪肉", "鸡鸭", "牛羊");
        callback.onSuccess("研磨".equals(majorCategory) ? null : list);
    }

    @Override
    public void loadLocalRecipes(String majorCategory, String minorCategory, int page, int pagesize, SyncCallback<List<Recipe>> callback) {
        String json;
        if (hsb) {
            json = "[{\"id\":\"1\",\"title\":\"浓汤\",\"duration\":15,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\",\"simple\":true}," +
                    "{\"id\":\"2\",\"title\":\"清汤\",\"duration\":30,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\",\"simple\":true}," +
                    "{\"id\":\"3\",\"title\":\"八宝米糊\",\"duration\":900,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"4\",\"title\":\"养生米粥\",\"duration\":1200,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"5\",\"title\":\"杂粮糊\",\"duration\":1500,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"8\",\"title\":\"阿水淀粉儿童问题是否感受过哥二哥\",\"duration\":900,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"9\",\"title\":\"人太皇太后\",\"duration\":100,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"10\",\"title\":\"突入体育\",\"duration\":900,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"11\",\"title\":\"搞活经济1234\",\"duration\":60,\"majorCategory\":\"冷饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"12\",\"title\":\"推荐推荐推荐\",\"duration\":600,\"majorCategory\":\"冷饮\",\"minorCategory\":\"汤类\"}" +
                    "]";
        } else {
            json = "[{\"id\":\"1\",\"title\":\"杂粮粥\",\"duration\":900,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"2\",\"title\":\"银耳红\",\"duration\":30,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"3\",\"title\":\"八宝米\",\"duration\":600,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"4\",\"title\":\"养生米\",\"duration\":1200,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"5\",\"title\":\"银耳红枣\",\"duration\":100,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"6\",\"title\":\"浓汤\",\"duration\":1500,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"7\",\"title\":\"清汤\",\"duration\":900,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"8\",\"title\":\"阿水淀粉儿童问题是否感受过哥二哥\",\"duration\":15,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"9\",\"title\":\"人太皇太后\",\"duration\":45,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"10\",\"title\":\"突入体育\",\"duration\":120,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"11\",\"title\":\"搞活经济1234\",\"duration\":1500,\"majorCategory\":\"水产类\",\"minorCategory\":\"牛羊\"}," +
                    "{\"id\":\"12\",\"title\":\"推荐推荐推荐\",\"duration\":1500,\"majorCategory\":\"水产类\",\"minorCategory\":\"牛羊\"}" +
                    "]";
        }
        List<Recipe> recipes = FastJsonUtil.json2List(json, Recipe.class);

        for (int i = recipes.size(); --i >= 0; ) {
            Recipe recipe = recipes.get(i);
            if (!TextUtils.isEmpty(majorCategory)) {
                if (!majorCategory.equals(recipe.getMajorCategory())) {
                    recipes.remove(i);
                    continue;
                }
            }
            if (!TextUtils.isEmpty(minorCategory)) {
                if (!minorCategory.equals(recipe.getMinorCategory())) {
                    recipes.remove(i);
                    continue;
                }
            }
            recipe.setLocal(true);
        }

        if (recipes.size() > 0) {
            recipes.get(0).setImageRes(R.drawable.pic_shoucang3);
        }
        if (recipes.size() > 1) {
            recipes.get(1).setImageUrl("https://www.baidu.com/img/bd_logo1.png");
        }

        setupAttributes(recipes);
        callback.onSuccess(recipes);
    }

    @Override
    public void loadFavorRecipes(int page, int pagesize, SyncCallback<List<Recipe>> callback) {
        String json = "";
        if (hsb) {
            json = "[{\"id\":\"1\",\"title\":\"精力汤\",\"duration\":180,\"orderno\":1,\"imageRes\":" + R.drawable.pic_shoucang3 + ",\"simple\":true}," +
                    "{\"id\":\"2\",\"title\":\"杂粮糊\",\"duration\":30,\"orderno\":2,\"imageRes\":" + R.drawable.pic_shoucang2 + "}," +
                    "{\"id\":\"3\",\"title\":\"八宝粥\",\"duration\":1500,\"orderno\":3,\"imageRes\":" + R.drawable.pic_shoucang5 + "}," +
                    "{\"id\":\"4\",\"title\":\"红枣云耳\",\"duration\":90,\"orderno\":4,\"imageRes\":" + R.drawable.pic_shoucang4 + "}," +
                    "{\"id\":\"5\",\"title\":\"收藏05\",\"duration\":1800,\"orderno\":5}," +
                    "{\"id\":\"6\",\"title\":\"收藏06\",\"duration\":900,\"orderno\":6}," +
                    "{\"id\":\"7\",\"title\":\"收藏07\",\"duration\":1500,\"orderno\":7}," +
                    "{\"id\":\"8\",\"title\":\"收藏08\",\"duration\":100,\"orderno\":8}," +
                    "{\"id\":\"9\",\"title\":\"收藏09\",\"duration\":150,\"orderno\":9}," +
                    "{\"id\":\"10\",\"title\":\"收藏10\",\"duration\":600,\"orderno\":10}," +
                    "{\"id\":\"11\",\"title\":\"收藏11\",\"duration\":720,\"orderno\":11}," +
                    "{\"id\":\"12\",\"title\":\"收藏12\",\"duration\":1500,\"orderno\":12}" +
                    "]";
        } else if (cm) {
            json = "[{\"id\":\"1\",\"title\":\"红烧牛肉\",\"duration\":75,\"orderno\":1,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"2\",\"title\":\"红烧排骨\",\"duration\":300,\"orderno\":2,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"3\",\"title\":\"三杯鸡\",\"duration\":1500,\"orderno\":3,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"4\",\"title\":\"鱼香肉丝\",\"duration\":1200,\"orderno\":4,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"5\",\"title\":\"收藏05\",\"duration\":60,\"orderno\":5}," +
                    "{\"id\":\"6\",\"title\":\"收藏06\",\"duration\":1500,\"orderno\":6}," +
                    "{\"id\":\"7\",\"title\":\"收藏07\",\"duration\":1200,\"orderno\":7}," +
                    "{\"id\":\"8\",\"title\":\"收藏08\",\"duration\":900,\"orderno\":8}," +
                    "{\"id\":\"9\",\"title\":\"收藏09\",\"duration\":600,\"orderno\":9}," +
                    "{\"id\":\"10\",\"title\":\"收藏10\",\"duration\":600,\"orderno\":10}," +
                    "{\"id\":\"11\",\"title\":\"收藏11\",\"duration\":720,\"orderno\":11}," +
                    "{\"id\":\"12\",\"title\":\"收藏12\",\"duration\":1500,\"orderno\":12}" +
                    "]";
        } else {
            json = "[{\"id\":\"1\",\"title\":\"香甜饭\",\"duration\":75,\"orderno\":1,\"stateHint\":\"保温中\",\"isShowDetailDuration\":true,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"2\",\"title\":\"快速饭\",\"duration\":300,\"orderno\":2,\"stateHint\":\"3.5碗\",\"isShowDetailDuration\":false,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"3\",\"title\":\"粥\",\"duration\":1500,\"orderno\":3,\"stateHint\":\"3.5碗\",\"isShowDetailDuration\":false,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"4\",\"title\":\"加热\",\"duration\":1200,\"orderno\":4,\"stateHint\":\"\",\"isShowDetailDuration\":false,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"5\",\"title\":\"保温\",\"duration\":60,\"orderno\":5,\"stateHint\":\"\",\"isShowDetailDuration\":false,\"imageRes\":" + R.drawable.pic_shoucang1 + "}," +
                    "{\"id\":\"6\",\"title\":\"收藏06\",\"duration\":1500,\"orderno\":6}," +
                    "{\"id\":\"7\",\"title\":\"收藏07\",\"duration\":1200,\"orderno\":7}," +
                    "{\"id\":\"8\",\"title\":\"收藏08\",\"duration\":900,\"orderno\":8}," +
                    "{\"id\":\"9\",\"title\":\"收藏09\",\"duration\":600,\"orderno\":9}," +
                    "{\"id\":\"10\",\"title\":\"收藏10\",\"duration\":600,\"orderno\":10}," +
                    "{\"id\":\"11\",\"title\":\"收藏11\",\"duration\":720,\"orderno\":11}," +
                    "{\"id\":\"12\",\"title\":\"收藏12\",\"duration\":1500,\"orderno\":12}" +
                    "]";
        }

        List<Recipe> recipes = FastJsonUtil.json2List(json, Recipe.class);
        if (recipes.size() > pagesize) {
            recipes = recipes.subList(0, pagesize);
        }
        for (Recipe recipe : recipes) {
            recipe.setLocal(true);
            recipe.setFavor(true);
        }
        setupAttributes(recipes);
        callback.onSuccess(recipes);
    }

    @Override
    public void saveLocalRecipes(List<Recipe> list, SyncCallback<List<Boolean>> callback) {

    }

    @Override
    public void addLocalRecipe(Recipe recipe, SyncCallback<List<Boolean>> callback) {

    }

    @Override
    public void deleteLocalRecipes(String id, SyncCallback<List<Boolean>> callback) {

    }

    @Override
    public void loadRemoteRecipes(String majorCategory, String minorCategory, int page, int pagesize, final SyncCallback<List<Recipe>> callback) {
        String json;
        if (hsb) {
            json = "[{\"id\":\"101\",\"title\":\"网络食谱1\",\"duration\":150,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\",\"simple\":true}," +
                    "{\"id\":\"102\",\"title\":\"网络食谱2\",\"duration\":90,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"103\",\"title\":\"网络食谱3\",\"duration\":1500,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"104\",\"title\":\"网络食谱4\",\"duration\":240,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"105\",\"title\":\"网络食谱5\",\"duration\":1500,\"majorCategory\":\"热饮\",\"minorCategory\":\"汤类\"}," +
                    "{\"id\":\"108\",\"title\":\"网络食谱6\",\"duration\":1800,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"109\",\"title\":\"网络食谱7\",\"duration\":3600,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"110\",\"title\":\"网络食谱8\",\"duration\":600,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"111\",\"title\":\"网络食谱9\",\"duration\":120,\"majorCategory\":\"热饮\",\"minorCategory\":\"粥类\"}," +
                    "{\"id\":\"112\",\"title\":\"网络食谱10\",\"duration\":60,\"majorCategory\":\"热饮\",\"minorCategory\":\"糊类\"}" +
                    "]";
        } else {
            json = "[{\"id\":\"101\",\"title\":\"网络食谱1\",\"duration\":75,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"102\",\"title\":\"网络食谱2\",\"duration\":300,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"103\",\"title\":\"网络食谱3\",\"duration\":1500,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"104\",\"title\":\"网络食谱4\",\"duration\":480,\"majorCategory\":\"肉类\",\"minorCategory\":\"猪肉\"}," +
                    "{\"id\":\"105\",\"title\":\"网络食谱5\",\"duration\":600,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"106\",\"title\":\"网络食谱6\",\"duration\":1500,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"107\",\"title\":\"网络食谱7\",\"duration\":900,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"108\",\"title\":\"网络食谱8\",\"duration\":1200,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"109\",\"title\":\"网络食谱9\",\"duration\":420,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"110\",\"title\":\"网络食谱10\",\"duration\":90,\"majorCategory\":\"肉类\",\"minorCategory\":\"鸡鸭\"}," +
                    "{\"id\":\"111\",\"title\":\"网络食谱11\",\"duration\":1800,\"majorCategory\":\"水产类\",\"minorCategory\":\"牛羊\"}," +
                    "{\"id\":\"112\",\"title\":\"网络食谱12\",\"duration\":15,\"majorCategory\":\"水产类\",\"minorCategory\":\"牛羊\"}" +
                    "]";
        }
        final List<Recipe> recipes = FastJsonUtil.json2List(json, Recipe.class);

        for (int i = recipes.size(); --i >= 0; ) {
            Recipe recipe = recipes.get(i);
            if (!TextUtils.isEmpty(majorCategory)) {
                if (!majorCategory.equals(recipe.getMajorCategory())) {
                    recipes.remove(i);
                    continue;
                }
            }
            if (!TextUtils.isEmpty(minorCategory)) {
                if (!minorCategory.equals(recipe.getMinorCategory())) {
                    recipes.remove(i);
                }
            }
        }

        setupAttributes(recipes);

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isActivityRunning()) {
                    ToastUtil.show("虚拟网络数据加载完成");
                    callback.onSuccess(recipes);
                }
            }
        }, 3000);
    }

    private void setupAttributes(List<Recipe> recipes) {
        if (recipes != null && !recipes.isEmpty()) {
            for (Recipe recipe : recipes) {
                setupAttributes(recipe);
            }
        }
    }

    private void setupAttributes(Recipe recipe) {
        recipe.setDescription("寒冷的腊八节，熬上一锅香浓的腊八粥，既应景又暖身");
        recipe.setDifficulty("简单");
        recipe.setCalorie("1340");
        recipe.setSuitableNumber("适合2-3人食用");
        if (Math.random() > 0.8) {
            recipe.setDemo("http://www.baidu.com");
        }
        recipe.setSteps(Arrays.asList("将主料洗净", "将主料和调料放入锅中调均，呃啊肉肉人物5 earwig一盒优惠让用户推荐很讨厌脱氧核糖回忆", "开始烹调"));
        String json = "[{\"name\":\"大米\",\"value\":30},{\"name\":\"红豆\",\"value\":50},{\"name\":\"绿豆\",\"value\":45},{\"name\":\"黄豆\",\"value\":35},{\"name\":\"银耳\",\"value\":25},{\"name\":\"红枣\",\"value\":15},{\"name\":\"水\",\"value\":300,\"unit\":\"ml\"},{\"name\":\"白糖\",\"value\":30}]";
        List<Recipe.Ingredient> ingredients = FastJsonUtil.json2List(json, Recipe.Ingredient.class);
        recipe.setIngredients(ingredients);
    }
}
