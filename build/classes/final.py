import random

class TreeNode:
    def __init__(self, room):
        self.room = room
        self.children = []

def add_room_to_tree(root, room):
    new_node = TreeNode(room)
    root.children.append(new_node)
    return new_node


def build_room_design(root, room_design):
    room_design.append(root.room)
    for child in root.children:
        build_room_design(child, room_design)
    return room_design

def check_constraints(root, constraints):
    def dfs(node, parent_room, garage_count, balcony_count):
        current_room = node.room
        if current_room == "Balcony":
            balcony_count += 1
        elif current_room == "Garage":
            garage_count += 1
        for constraint in constraints:
            room1, room2 = constraint
            if (room1 == current_room and room2 == parent_room) or (room2 == current_room and room1 == parent_room):
                return False, garage_count, balcony_count
        for child in node.children:
            valid, garage_count, balcony_count = dfs(child, current_room, garage_count, balcony_count)
            if not valid:
                return False, garage_count, balcony_count
        return True, garage_count, balcony_count
    valid, garage_count, balcony_count = dfs(root, None, 0, 0)
    if not valid:
        return False
    if garage_count > 1 or balcony_count > 1 or (garage_count == 1 and balcony_count == 1):
        return False
    if garage_count == 1 and root.room != "Garage":
        return False
    if balcony_count == 1 and root.room != "Balcony":
        return False
    return True



def permutations(arr):
    if len(arr) == 1:
        return [arr]
    perms = []
    for i in range(len(arr)):
        first = arr[i]
        rest = arr[:i] + arr[i+1:]
        for p in permutations(rest):
            perms.append([first] + p)
    return perms

def rearrange_rooms(root, constraints):
    room_designs = []  # List to store all valid room designs

    room_design = []  # Temporary list to store each permutation
    build_room_design(root, room_design)  # Initial permutation based on the tree

    # Generate permutations
    room_permutations = permutations(room_design)
    for permutation in room_permutations:
        new_root = TreeNode(permutation[0])
        current_node = new_root
        for room in permutation[1:]:
            current_node = add_room_to_tree(current_node, room)
        if check_constraints(new_root, constraints):
            room_designs.append(permutation)  # Store valid permutation
    return room_designs



def main(rd):
    constraints = [
        ("Kitchen", "Master Bedroom (with bathroom)"),
        ("Store", "Master Bedroom (with bathroom)"),
        ("Bathroom", "Master Bedroom (with bathroom)"),
        ("Kitchen", "Guest Room (with bathroom)"),
        ("Store", "Guest Room (with bathroom)"),
        ("Bathroom", "Guest Room (with bathroom)")
    ]
    print(rd)
    user_selection = {}

    total_area = int(rd[0])

    room_sizes = {}
    for i in range(1, len(rd)):
        room_info = rd[i].split(", ")  # Split the string into room and size
        room = room_info[0]
        size = room_info[1]
        room_sizes[room] = size
    root = None
    current_node = root

    total_selected_area = 0  # Variable to track the total selected area

    for room_info in rd[1:]:
        room, size = room_info.split(", ")  # Split the string into room and size
        if root is None:
            root = TreeNode(room)
            current_node = root
        else:
            current_node = add_room_to_tree(current_node, room)
        user_selection[room] = size
        total_selected_area += int(size)  # Add the size of the current room to the total selected area

    if total_selected_area > total_area:  # Check if total selected area exceeds the total area of the house
        print("The total area of the selected rooms exceeds the total area specified for the house.")
        return


    arr = [["Constraints violated. Unable to generate valid room designs."]]
    room_designs = rearrange_rooms(root, constraints)
    if room_designs:
        random.shuffle(room_designs)
        print("Possible room designs:")
        for design in room_designs:
            print(design)
        # if total_area - total_selected_area > 0:
        #     print(f"You have {total_area - total_selected_area} square units left to fill.")
        return room_designs
    else:
        # print("Constraints violated. Unable to generate valid room designs.")
        return arr

rd = [
    "2000",
    "Kitchen, 48",
    "Lounge Area, 600",
    "Garage, 120",
]

print(main(rd))

