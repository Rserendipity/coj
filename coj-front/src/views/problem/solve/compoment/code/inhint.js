import * as monaco from 'monaco-editor'
import {language as cppLanguage} from 'monaco-editor/esm/vs/basic-languages/cpp/cpp.js';
import {language as javaLanguage} from 'monaco-editor/esm/vs/basic-languages/java/java.js';

const cppCompletionFunc = () => {
    monaco.languages.registerCompletionItemProvider('cpp', {
        provideCompletionItems: function () {
            let suggestions = [];
            cppLanguage.keywords.forEach(item => {
                suggestions.push({
                    label: item,
                    kind: monaco.languages.CompletionItemKind.Keyword,
                    insertText: item
                });
            })
            cppLanguage.operators.forEach(item => {
                suggestions.push({
                    label: item,
                    kind: monaco.languages.CompletionItemKind.Operator,
                    insertText: item
                });
            })
            return {
                suggestions: suggestions
            };
        },
    });
}

const javaCompletionFunc = () => {
    monaco.languages.registerCompletionItemProvider('java', {
        provideCompletionItems: function () {
            let suggestions = [];
            javaLanguage.keywords.forEach(item => {
                suggestions.push({
                    label: item,
                    kind: monaco.languages.CompletionItemKind.Keyword,
                    insertText: item
                });
            })
            javaLanguage.operators.forEach(item => {
                suggestions.push({
                    label: item,
                    kind: monaco.languages.CompletionItemKind.Operator,
                    insertText: item
                });
            })
            suggestions.push({
                label: "Class",
                kind: monaco.languages.CompletionItemKind.Class,
                insertText: "Class"
            });
            suggestions.push({
                label: "Interface",
                kind: monaco.languages.CompletionItemKind.Interface,
                insertText: "Interface"
            });
            return {
                suggestions: suggestions
            };
        },
    })
};

export {
    cppCompletionFunc,
    javaCompletionFunc
};
