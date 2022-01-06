#!/bin/bash

GIT_ROOT=$(git rev-parse --show-toplevel)
GIT_HOOK_PATH=${GIT_ROOT}/.git/hooks
GIT_MY_HOOK_PATH=${GIT_ROOT}/frontend/git_hooks

# adding git hook files below array
HOOK_FILES=("pre-commit")

for file in "${HOOK_FILES[@]}"
do
    cp $GIT_MY_HOOK_PATH/$file $GIT_HOOK_PATH
    chmod +x $GIT_HOOK_PATH/$file
done