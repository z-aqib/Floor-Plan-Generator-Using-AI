import csv  # Import the CSV module for reading CSV files
import sys  # Import the sys module for system-specific functions

# Define a class TreeNode to represent a node in the tree
class TreeNode:
    def __init__(self, room):
        self.room = room  # Initialize the node with a room name
        self.children = []  # Initialize an empty list to store children nodes

# Function to add a room to the tree as a child of a given node
def add_room_to_tree(root, room):
    new_node = TreeNode(room)  # Create a new node for the room
    root.children.append(new_node)  # Add the new node as a child of the given node
    return new_node  # Return the newly created node

# Function to build a list of room names in the tree starting from a given node
def build_room_design(root, room_design):
    room_design.append(root.room)  # Add the room name of the current node to the list
    for child in root.children:
        build_room_design(child, room_design)  # Recursively build the list for each child node
    return room_design

# Function to check if the constraints are satisfied in the tree
def check_constraints(root, constraints):
    def dfs(node, parent_room):
        if parent_room:
            current_room = node.room
            for constraint in constraints:
                room1, room2 = constraint
                # Check if the current node and its parent violate any constraint
                if (room1 == current_room and room2 == parent_room) or (room2 == current_room and room1 == parent_room):
                    return False
        for child in node.children:
            if not dfs(child, node.room):
                return False
        return True

    return dfs(root, None)

# Main function to run the house design planner
def main():
    constraints = [
        ("kitchen", "master bedroom with bathroom"),
        ("dirty kitchen", "master bedroom with bathroom"),
        ("store", "master bedroom with bathroom"),
        ("bathroom", "master bedroom with bathroom"),
        ("kitchen", "guest room with bathroom"),
        ("dirty kitchen", "guest room with bathroom"),
        ("store", "guest room with bathroom"),
        ("bathroom", "guest room with bathroom")
    ]

    user_selection = {}  # Dictionary to store user-selected rooms and sizes

    print("Welcome to the house design planner!")
    total_area = int(input("Enter the total area for your house: "))

    room_sizes = {}  # Dictionary to store room sizes read from CSV file
    with open('AIProjectData.csv', newline='') as csvfile:
        reader = csv.reader(csvfile)
        for row in reader:
            if len(row) >= 4:
                room = row[0]
                size = int(row[1])
                length = int(row[2])
                width = int(row[3])
                if room not in room_sizes:
                    room_sizes[room] = {}
                room_sizes[room][size] = length * width
            else:
                print("Invalid row format in CSV file.")
                sys.exit(1)

    print("Available rooms:")
    for i, room in enumerate(room_sizes.keys(), 1):
        print(f"{i}. {room}")

    root = None  # Initialize the root node of the tree
    current_node = root  # Initialize the current node to the root

    while True:
        choice = input("Enter the number of the room you want to add (or enter 'done' to finish): ")
        if choice.lower() == "done":
            break
        try:
            room_index = int(choice) - 1
            if 0 <= room_index < len(room_sizes):
                room = list(room_sizes.keys())[room_index]
                print(f"Available sizes for {room}:")
                for size, area in room_sizes[room].items():
                    print(f"{size}. Size: {area}")
                size_choice = int(input("Enter the number of the size you want: "))
                if size_choice in room_sizes[room]:
                    user_selection[room] = size_choice
                    if root is None:
                        root = TreeNode(room)
                        current_node = root
                    else:
                        current_node = add_room_to_tree(current_node, room)
                else:
                    print("Invalid size choice. Please enter a number from the list.")
            else:
                print("Invalid room number. Please enter a number from the list.")
        except ValueError:
            print("Invalid input. Please enter a number or 'done'.")

    total_selected_area = sum(room_sizes[room][size] for room, size in user_selection.items())
    if total_selected_area > total_area:
        print("The total area of the selected rooms does not match the total area specified for the house.")
        return

    room_design = []  # List to store the final room design
    if check_constraints(root, constraints):
        print("\nRoom design:")
        room_design = build_room_design(root, room_design)  # Build the room design list
        print(room_design)
        if (total_area - total_selected_area> 0):
            print(f"You have {total_area - total_selected_area} square units left to fill.")
    else:
        print("Constraints violated. Unable to generate valid room design.")
        return None

# Run the main function if the script is executed directly
if __name__ == "__main__":
    main()
