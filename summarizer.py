import subprocess
import csv
import os

def get_git_commit_messages():
    try:
        # Get commit messages using git log
        result = subprocess.run(
            ["git", "log", "--pretty=format:%s"],
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
            check=True
        )
        messages = result.stdout.strip().split('\n')
        return messages
    except subprocess.CalledProcessError as e:
        print("Error running git log:", e.stderr)
        return []

def write_to_csv(messages, filename="commits.csv"):
    with open(filename, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(["Commit Message"])
        for msg in messages:
            writer.writerow([msg])
    print(f"Saved {len(messages)} commit messages to {filename}")

if __name__ == "__main__":
    if not os.path.isdir(".git"):
        print("This directory is not a Git repository.")
    else:
        commit_messages = get_git_commit_messages()
        write_to_csv(commit_messages)
