#include <iostream>
using namespace std;

int main() {
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int len;
        cin >> len;
        int cnt = 0;
        for (int j = 0; j < len; j++) {
            int value;
            cin >> value;
            cnt += value;
        }
        cout << cnt << endl;
    }
    return 0;
}