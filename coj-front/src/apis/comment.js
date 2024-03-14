import httpInstance from "@/utils/httpInstance";

export function getCommentAPI(id) {
    return httpInstance.get(`/comment/get?problemId=${id}`);
}


export function uploadCommentAPI(info) {
    return httpInstance.post(`/comment/add`, info);
}
