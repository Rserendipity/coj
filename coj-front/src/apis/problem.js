import httpInstance from "@/utils/httpInstance";

export function getProblemListAPI() {
  return httpInstance.get(`/problem/problem_list`);
}

export function getProblemDetailAPI(id) {
  return httpInstance.get(`/problem/problem_detail?id=${id}`);
}

export function updateQuestionAPI(data, id) {
  if (id) {
    return httpInstance.post(`/problem/upload_problem?id=${id}`, data);
  } else {
    return httpInstance.post(`/problem/upload_problem`, data);
  }
}

export function deleteProblemAPI(id) {
  return httpInstance.post(`/problem/delete_problem?id=${id}`);
}

export function getProblemAnswerAPI(id) {
  return httpInstance.get(`/problem/get_answer?id=${id}`);
}

export function uploadProblemAnswerAPI(answer) {
  return httpInstance.post(`/problem/submit_answer`, answer);
}