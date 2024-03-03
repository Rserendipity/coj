// 引入 markdown 编辑器依赖
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
// 引入 markdown 预览依赖
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import hljs from 'highlight.js';

VueMarkdownEditor.use(vuepressTheme, {
  Prism,
});

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

// 暴露组件依赖
export {VueMarkdownEditor, VMdPreview};