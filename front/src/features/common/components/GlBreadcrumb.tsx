import {Breadcrumb} from "antd";
import {Link, useLocation} from "react-router-dom";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
function itemRender(currentRoute, params, items, paths) {
    const isLast = currentRoute?.path === items[items.length - 1]?.path;
    return isLast ? (
        <span>{currentRoute.title}</span>
    ) : (
        <Link to={`/${paths.join('/')}`}>{currentRoute.title}</Link>
    );
}

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
const GlBreadcrumb: React.FC<GlBreadcrumbProps> = ({style})=> {
    const location = useLocation();
    const pathSnippets = location.pathname.split('/').filter((_, index) => index !== 0);

    // 生成面包屑项数组
    const items = pathSnippets.map((snippet, index) => ({
        title: snippet.charAt(0).toUpperCase() + snippet.slice(1), // 首字母大写（按需修改）
        path: `/${pathSnippets.slice(0, index + 1).join('/')}`
    }));

    return (
        <Breadcrumb
            itemRender={itemRender}
            style={style}
            items={items}
        />
    )
}

export default GlBreadcrumb;